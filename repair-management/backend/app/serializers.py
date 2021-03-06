from rest_framework import serializers

from .models import Facility, WorkOrder


class FacilityReadSerializer(serializers.ModelSerializer):

    class Meta:

        model = Facility
        fields = '__all__'


class FacilityWriteSerializer(serializers.ModelSerializer):

    class Meta:

        model = Facility
        fields = ('id', 'name', 'address', 'zip_code', 'city', 'province', 'country')


class WorkOrderSerializer(serializers.ModelSerializer):

    class Meta:
        model = WorkOrder
        facility_id = serializers.PrimaryKeyRelatedField(queryset=Facility.objects.filter(active=True))
        fields = '__all__'

