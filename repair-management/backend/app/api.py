from rest_framework import routers

from . import views

router = routers.DefaultRouter()
router.register(r'facilities', views.FacilityViewSet)
router.register(r'work-orders', views.WorkOrderViewSet)

