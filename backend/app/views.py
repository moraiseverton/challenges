from rest_framework import viewsets

from . import models
from .mixins import ReadWriteSerializerMixin
from .serializers import FacilityReadSerializer, FacilityWriteSerializer


class FacilityList(ReadWriteSerializerMixin, viewsets.ModelViewSet):
    queryset = models.Facility.objects.all()
    read_serializer_class = FacilityReadSerializer
    write_serializer_class = FacilityWriteSerializer
    http_method_names = ['get', 'post', 'put']

