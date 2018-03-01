from django.conf.urls import  url
from . import views

urlpatterns =  [
    url(r'updateinformations',views.updateinformations,name="updateinformations")
]