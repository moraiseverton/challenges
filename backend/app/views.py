from rest_framework import viewsets
from . import models
from . import serializers


class FacilityList(viewsets.ModelViewSet):
    queryset = models.Facility.objects.all()
    serializer_class = serializers.FacilitySerializer

