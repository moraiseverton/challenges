from rest_framework import status, viewsets
from rest_framework.decorators import action
from rest_framework.response import Response

from .mixins import ReadWriteSerializerMixin
from .models import Facility
from .serializers import FacilityReadSerializer, FacilityWriteSerializer


class FacilityList(ReadWriteSerializerMixin, viewsets.ModelViewSet):
    queryset = Facility.objects.all()
    read_serializer_class = FacilityReadSerializer
    write_serializer_class = FacilityWriteSerializer
    http_method_names = ['get', 'post', 'put']


@action(methods=['POST'], detail=True, name='Deactivate Facility', url_path='deactivate')
def deactivate(self, request, pk=None):
    facility = Facility.objects.get(pk=pk)

    if facility.active:
        facility.active = False
        facility.save()
        return Response(data=facility,
                        status=status.HTTP_200_OK)
    else:
        return Response('Facility already deactivated.',
                        status=status.HTTP_409_CONFLICT)



