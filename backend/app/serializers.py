from rest_framework import serializers

from .models import Facility


class FacilityReadSerializer(serializers.ModelSerializer):

    class Meta:

        model = Facility
        fields = '__all__'


class FacilityWriteSerializer(serializers.ModelSerializer):

    class Meta:

        model = Facility
        fields = ('id', 'name', 'address', 'zip_code', 'city', 'province', 'country')


