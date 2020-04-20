from django.test import TestCase
from rest_framework.test import APIRequestFactory

from .models import Facility, WorkOrder
from .serializers import FacilityReadSerializer, WorkOrderSerializer
from .views import FacilityViewSet, deactivate, WorkOrderViewSet


class FacilityViewSetTest(TestCase):

    def test_list_do_not_list_deactivated_facilities(self):
        factory = APIRequestFactory()
        view = FacilityViewSet.as_view(actions={
            'get': 'list',
        })
        Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                city="Winnipeg", province="MB", country="CA")

        Facility.objects.create(name="John's Deli", address="887 Cushion Ave", zip_code="Z3I0O1",
                                city="Calgary", province="AB", country="CA")

        Facility.objects.create(name="Charles's Deli", address="23 Ross Ave", zip_code="O1V3W9",
                                city="Toronto", province="ON", country="CA", active=False)

        request = factory.get('')
        response = view(request)

        expected_facilities = Facility.objects.filter(active=True)
        expected_serializer = FacilityReadSerializer(expected_facilities, many=True)
        expected_data = expected_serializer.data

        self.assertEqual(response.status_code, 200)
        self.assertEqual(expected_data, response.data)

    def test_list_when_partial_name_and_partial_province_exists_filters_correctly(self):
        factory = APIRequestFactory()
        view = FacilityViewSet.as_view(actions={
            'get': 'list',
        })
        Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                city="Winnipeg", province="MB", country="CA")

        Facility.objects.create(name="John's Deli", address="887 Cushion Ave", zip_code="Z3I0O1",
                                city="Calgary", province="AB", country="CA")

        Facility.objects.create(name="Johnathan's Deli", address="12 Blue St", zip_code="L3F5I2",
                                city="Edmonton", province="AB", country="CA")

        Facility.objects.create(name="Johnny's Deli", address="1019 Erin Ave", zip_code="N3O0O1",
                                city="Ottawa", province="ON", country="CA")

        Facility.objects.create(name="John's Deli", address="87 Smith St", zip_code="M3B0O1",
                                city="Brandon", province="MB", country="CA")

        Facility.objects.create(name="Charles's Deli", address="23 Ross Ave", zip_code="O1V3W9",
                                city="Toronto", province="ON", country="CA", active=False)

        Facility.objects.create(name="Joanne's Deli", address="57 Portage St", zip_code="Y6T5R4",
                                city="Calgary", province="AB", country="CA")

        request = factory.get('', data={'partialName': 'john', 'partialProvince': 'ab'})
        response = view(request)

        expected_facilities = Facility.objects.filter(province='AB', name__contains='John')
        expected_serializer = FacilityReadSerializer(expected_facilities, many=True)
        expected_data = expected_serializer.data

        self.assertEqual(response.status_code, 200)
        self.assertEqual(expected_data, response.data)

    def test_deactivate_when_facility_is_active_deactivates_correctly(self):
        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        factory = APIRequestFactory()

        url = '/%s/deactivate' % facility.pk
        request = factory.post(url)
        response = deactivate(request, facility.pk)

        self.assertEqual(response.status_code, 200)

    def test_deactivate_when_facility_is_already_deactivated_returns_conflict(self):
        facility = Facility.objects.create(name="Charles's Deli", address="23 Ross Ave", zip_code="O1V3W9",
                                           city="Toronto", province="ON", country="CA", active=False)

        factory = APIRequestFactory()

        url = '/%s/deactivate' % facility.pk
        request = factory.post(url)
        response = deactivate(request, facility.pk)

        self.assertEqual(response.status_code, 409)


class WorkOrderViewSetTest(TestCase):

    def test_create_creates_correctly(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'post': 'create',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        request = factory.post('', data={'title': 'Replace light bulb',
                                         'description': 'It needs to be replaced ASAP!',
                                         'status': 'NEW',
                                         'facility_id': facility.pk, })
        response = create(request)
        resulted_id = response.data.get('id')

        expected_work_order = WorkOrder.objects.get(pk=resulted_id)
        expected_serializer = WorkOrderSerializer(expected_work_order, many=False)
        expected_data = expected_serializer.data

        self.assertEqual(response.status_code, 201)
        self.assertEqual(expected_data, response.data)

    def test_create_when_status_is_started_creates_correctly(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'post': 'create',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        request = factory.post('', data={'title': 'Replace light bulb',
                                         'description': 'It needs to be replaced ASAP!',
                                         'status': 'STARTED',
                                         'facility_id': facility.pk, })
        response = create(request)
        resulted_id = response.data.get('id')

        expected_work_order = WorkOrder.objects.get(pk=resulted_id)
        expected_serializer = WorkOrderSerializer(expected_work_order, many=False)
        expected_data = expected_serializer.data

        self.assertEqual(response.status_code, 201)
        self.assertEqual(expected_data, response.data)

    def test_create_when_status_is_completed_raises_exception(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'post': 'create',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        request = factory.post('', data={'title': 'Replace light bulb',
                                         'description': 'It needs to be replaced ASAP!',
                                         'status': 'COMPLETED',
                                         'facility_id': facility.pk, })
        response = create(request)

        self.assertEqual(response.status_code, 400)

    def test_create_when_status_is_cancelled_raises_exception(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'post': 'create',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        request = factory.post('', data={'title': 'Replace light bulb',
                                         'description': 'It needs to be replaced ASAP!',
                                         'status': 'CANCELLED',
                                         'facility_id': facility.pk, })
        response = create(request)

        self.assertEqual(response.status_code, 400)

    def test_create_when_facility_is_deactivated_raises_exception(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'post': 'create',
        })

        facility = Facility.objects.create(name="Charles's Deli", address="23 Ross Ave", zip_code="O1V3W9",
                                           city="Toronto", province="ON", country="CA", active=False)

        request = factory.post('', data={'title': 'Replace light bulb',
                                         'description': 'It needs to be replaced ASAP!',
                                         'status': 'NEW',
                                         'facility_id': facility.pk, })
        response = create(request)

        self.assertEqual(response.status_code, 400)

    def test_create_when_facility_does_not_exist_raises_exception(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'post': 'create',
        })

        request = factory.post('', data={'title': 'Replace light bulb',
                                         'description': 'It needs to be replaced ASAP!',
                                         'status': 'NEW',
                                         'facility_id': '7', })
        response = create(request)

        self.assertEqual(response.status_code, 400)

    def test_update_updates_correctly(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'put': 'update',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        work_order = WorkOrder.objects.create(title='Replace light bulb',
                                              description='It needs to be replaced ASAP!',
                                              status='NEW', facility_id=facility)

        request = factory.put('', data={'title': 'Replace light bulb',
                                        'description': 'It needs to be replaced ASAP!',
                                        'status': 'NEW',
                                        'facility_id': facility.pk, })

        response = create(request, work_order.pk)

        expected_work_order = WorkOrder.objects.get(pk=work_order.pk)
        expected_serializer = WorkOrderSerializer(expected_work_order, many=False)
        expected_data = expected_serializer.data

        self.assertEqual(response.status_code, 200)
        self.assertEqual(expected_data, response.data)

    def test_update_when_starts_a_work_order_starts_correctly(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'put': 'update',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        work_order = WorkOrder.objects.create(title='Replace light bulb',
                                              description='It needs to be replaced ASAP!',
                                              status='NEW', facility_id=facility)

        request = factory.put('', data={'title': 'Replace light bulb',
                                        'description': 'It needs to be replaced ASAP! It is all dark here.',
                                        'status': 'STARTED',
                                        'facility_id': facility.pk, })

        response = create(request, work_order.pk)

        expected_work_order = WorkOrder.objects.get(pk=work_order.pk)
        expected_serializer = WorkOrderSerializer(expected_work_order, many=False)
        expected_data = expected_serializer.data

        self.assertEqual(response.status_code, 200)
        self.assertEqual(expected_data, response.data)

    def test_update_when_completes_a_work_order_completes_correctly(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'put': 'update',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        work_order = WorkOrder.objects.create(title='Replace light bulb',
                                              description='It needs to be replaced ASAP!',
                                              status='STARTED', facility_id=facility)

        request = factory.put('', data={'title': 'Replace light bulb',
                                        'description': 'It needs to be replaced ASAP!',
                                        'status': 'COMPLETED',
                                        'facility_id': facility.pk, })

        response = create(request, work_order.pk)

        expected_work_order = WorkOrder.objects.get(pk=work_order.pk)
        expected_serializer = WorkOrderSerializer(expected_work_order, many=False)
        expected_data = expected_serializer.data

        self.assertEqual(response.status_code, 200)
        self.assertEqual(expected_data, response.data)

    def test_update_when_cancels_a_work_order_cancels_correctly(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'put': 'update',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        work_order = WorkOrder.objects.create(title='Replace light bulb',
                                              description='It needs to be replaced ASAP!',
                                              status='NEW', facility_id=facility)

        request = factory.put('', data={'title': 'Replace light bulb',
                                        'description': 'Cancelling the work order because we replaced by ourselves.',
                                        'status': 'CANCELLED',
                                        'facility_id': facility.pk, })

        response = create(request, work_order.pk)

        expected_work_order = WorkOrder.objects.get(pk=work_order.pk)
        expected_serializer = WorkOrderSerializer(expected_work_order, many=False)
        expected_data = expected_serializer.data

        self.assertEqual(response.status_code, 200)
        self.assertEqual(expected_data, response.data)

    def test_update_when_new_facility_is_deactivated_raises_exception(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'put': 'update',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        deactivated_facility = Facility.objects.create(name="Charles's Deli", address="23 Ross Ave", zip_code="O1V3W9",
                                                       city="Toronto", province="ON", country="CA", active=False)

        work_order = WorkOrder.objects.create(title='Replace light bulb',
                                              description='It needs to be replaced ASAP!',
                                              status='NEW', facility_id=facility)

        request = factory.put('', data={'title': 'Replace light bulb',
                                        'description': 'It needs to be replaced ASAP!',
                                        'status': 'STARTED',
                                        'facility_id': deactivated_facility.pk, })

        response = create(request, work_order.pk)

        self.assertEqual(response.status_code, 400)

    def test_update_when_title_is_changed_raises_exception(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'put': 'update',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        work_order = WorkOrder.objects.create(title='Replace light bulb',
                                              description='It needs to be replaced ASAP!',
                                              status='NEW', facility_id=facility)

        request = factory.put('', data={'title': 'Replace two light bulbs',
                                        'description': 'It needs to be replaced ASAP!',
                                        'status': 'STARTED',
                                        'facility_id': facility.pk, })

        response = create(request, work_order.pk)

        self.assertEqual(response.status_code, 400)

    def test_update_when_description_is_changed_raises_exception(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'put': 'update',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        work_order = WorkOrder.objects.create(title='Replace light bulb',
                                              description='It needs to be replaced ASAP!',
                                              status='STARTED', facility_id=facility)

        request = factory.put('', data={'title': 'Replace light bulb',
                                        'description': 'It needs to be replaced ASAP! It is all dark here.',
                                        'status': 'STARTED',
                                        'facility_id': facility.pk, })

        response = create(request, work_order.pk)

        self.assertEqual(response.status_code, 400)

    def test_update_when_cancels_a_started_work_order_raises_exception(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'put': 'update',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        work_order = WorkOrder.objects.create(title='Replace light bulb',
                                              description='It needs to be replaced ASAP!',
                                              status='STARTED', facility_id=facility)

        request = factory.put('', data={'title': 'Replace light bulb',
                                        'description': 'It needs to be replaced ASAP!',
                                        'status': 'CANCELLED',
                                        'facility_id': facility.pk, })

        response = create(request, work_order.pk)

        self.assertEqual(response.status_code, 400)

    def test_update_when_completes_a_not_started_work_order_raises_exception(self):
        factory = APIRequestFactory()
        create = WorkOrderViewSet.as_view(actions={
            'put': 'update',
        })

        facility = Facility.objects.create(name="Mark's Deli", address="125 Research Drive", zip_code="R1C0P8",
                                           city="Winnipeg", province="MB", country="CA")

        work_order = WorkOrder.objects.create(title='Replace light bulb',
                                              description='It needs to be replaced ASAP!',
                                              status='NEW', facility_id=facility)

        request = factory.put('', data={'title': 'Replace light bulb',
                                        'description': 'It needs to be replaced ASAP!',
                                        'status': 'COMPLETED',
                                        'facility_id': facility.pk, })

        response = create(request, work_order.pk)

        self.assertEqual(response.status_code, 400)
