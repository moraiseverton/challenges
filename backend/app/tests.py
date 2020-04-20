from django.test import TestCase
from rest_framework.test import APIRequestFactory

from .models import Facility
from .serializers import FacilityReadSerializer
from .views import FacilityViewSet, deactivate


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

    def test_deactivate_when_facility_is_active_then_deactivates_correctly(self):
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

