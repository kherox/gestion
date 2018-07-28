from django.db import models
from django.contrib.auth.models import User

from activities.models import Activitie
from clients.models import Client

# Create your models here.

class Project(models.Model) :
    name            = models.CharField(max_length=250)
    activities      = models.ForeignKey(Activitie,on_delete=models.PROTECT)
    client          = models.ForeignKey(Client,on_delete=models.PROTECT)
    personneabout   = models.CharField(max_length=150)
    ville           = models.CharField(max_length=25)
    description     = models.TextField()
    created         = models.DateField(auto_now_add=True)
    marge           = models.FloatField(verbose_name=None,blank=False,null=True,default=1.0,editable=True)
    remise          = models.FloatField(verbose_name=None,blank=False,null=True,default=1.0,editable=True)
    user            = models.ForeignKey(User, on_delete=models.PROTECT)
    



    def __str__(self) :
        return self.name