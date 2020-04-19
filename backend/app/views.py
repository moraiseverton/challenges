from django.http import JsonResponse
from rest_framework import viewsets
from rest_framework.decorators import action

from .mixins import ReadWriteSerializerMixin
from .models import Facility
from .serializers import FacilityReadSerializer, FacilityWriteSerializer


class FacilityList(ReadWriteSerializerMixin, viewsets.ModelViewSet):
    queryset = Facility.objects.all()
    read_serializer_class = FacilityReadSerializer
    write_serializer_class = FacilityWriteSerializer
    http_method_names = ['get', 'post', 'put']


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
