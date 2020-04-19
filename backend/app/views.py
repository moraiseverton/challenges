from django.http import JsonResponse
from rest_framework import viewsets
from rest_framework.decorators import action
from rest_framework.response import Response

from .mixins import ReadWriteSerializerMixin
from .models import Facility
from .serializers import FacilityReadSerializer, FacilityWriteSerializer


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
