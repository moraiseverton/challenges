from django.http import JsonResponse, HttpResponseBadRequest
from rest_framework import viewsets
from rest_framework.decorators import action
from rest_framework.exceptions import ValidationError
from rest_framework.response import Response
from rest_framework.status import HTTP_404_NOT_FOUND, HTTP_403_FORBIDDEN, HTTP_201_CREATED

from .mixins import ReadWriteSerializerMixin
from .models import Facility, WorkOrder
from .serializers import FacilityReadSerializer, FacilityWriteSerializer, WorkOrderSerializer


class FacilityViewSet(ReadWriteSerializerMixin, viewsets.ModelViewSet):
    queryset = Facility.objects.filter()
    read_serializer_class = FacilityReadSerializer
    write_serializer_class = FacilityWriteSerializer
    http_method_names = ['get', 'post', 'put']
    search_fields = ('^name', '^province')
    filter_fields = ('name', 'province')

    def list(self, request):
        facilities = self.queryset.filter(active=True)

        partial_name = request.query_params.get('partialName', None)
        if partial_name is not None:
            facilities = facilities.filter(name__contains=partial_name)

        partial_province = request.query_params.get('partialProvince', None)
        if partial_province is not None:
            facilities = facilities.filter(province__contains=partial_province)

        page = self.filter_queryset(facilities)

        serializer = self.get_serializer(page, many=True)
        result_set = serializer.data

        return Response(result_set)


@action(methods=['post'], detail=True, name='Deactivate Facility', url_path='deactivate')
def deactivate(request, id=None):
    facility = Facility.objects.get(pk=id)

    if facility.active:
        facility.active = False
        facility.save()
        serializer = FacilityReadSerializer(facility, many=False)
        return JsonResponse(serializer.data, safe=False, status=200)
    else:
        return JsonResponse({'error': 'facility already deactivated'},
                            status=409)


class WorkOrderViewSet(viewsets.ModelViewSet):
    queryset = WorkOrder.objects.all()
    serializer_class = WorkOrderSerializer
    http_method_names = ['get', 'post', 'put', 'head', 'options']

    def create(self, request, *args, **kwargs):
        self.validate_active_facility(request.data.get('facility_id'))

        if request.data.get('status') in ['CANCELLED', 'COMPLETED']:
            raise ValidationError("Work order must be created as New or Started.",
                                  code=HTTP_403_FORBIDDEN)

        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)
        self.perform_create(serializer)
        return Response(serializer.data, status=HTTP_201_CREATED)

    def perform_create(self, serializer):
        serializer.save()

    def update(self, request, pk=None, *args, **kwargs):
        try:
            instance = WorkOrder.objects.get(pk=pk)
        except Exception:
            raise ValidationError('Work order not found.', code=HTTP_404_NOT_FOUND)

        updated_data = request.data
        if self.validate(instance, updated_data):
            instance.title = updated_data.get('title', instance.title)
            instance.description = updated_data.get('description', instance.description)
            instance.status = updated_data.get('status', instance.status)
            instance.facility_id = Facility.objects.get(pk=updated_data.get('facility_id'))
            instance.save()

            serializer = self.get_serializer(instance, many=False)
            return Response(serializer.data)

        return HttpResponseBadRequest({'error': 'Work order not updated'})

    def validate(self, current_instance, updated_data):
        self.validate_active_facility(updated_data.get('facility_id'))

        if updated_data.get('title') != current_instance.title:
            raise ValidationError("Update title not allowed.", code=HTTP_403_FORBIDDEN)

        current_status = current_instance.status
        updated_status = updated_data.get('status')

        if updated_data.get('description') != current_instance.description and current_status != 'NEW':
            raise ValidationError('Description cannot be updated after started.',
                                  code=HTTP_403_FORBIDDEN)

        if updated_status == 'CANCELLED' and current_status == 'STARTED':
            raise ValidationError("Work order can only be cancelled before it is started.",
                                  code=HTTP_403_FORBIDDEN)

        if updated_status == 'COMPLETED' and current_status != 'STARTED':
            raise ValidationError("Work order must be started before being completed.",
                                  code=HTTP_403_FORBIDDEN)

        return True

    @staticmethod
    def validate_active_facility(facility_id):
        try:
            facility = Facility.objects.get(pk=facility_id)
        except Exception:
            raise ValidationError('Facility not found.', code=HTTP_404_NOT_FOUND)

        if not facility.active:
            raise ValidationError('Work orders can only be created for active facilities.',
                                  code=HTTP_403_FORBIDDEN)

