# -*- coding: utf-8 -*-

from django.db import models
from django.contrib.auth.models import User

from activities.models import Activitie
# Create your models here.

class Client(models.Model) :
    clientcode      = models.CharField(max_length=25)
    clientname      = models.CharField(max_length=150)
    clientraison    = models.CharField(max_length=150)
    clientcontact   = models.CharField(max_length=15)
    clientcity      = models.CharField(max_length=150)
    clientcountry   = models.CharField(max_length=150)
    clientfax       = models.CharField(max_length=150)
    clientemail     = models.EmailField(max_length=250)
    clientreglement = models.CharField(max_length=50)
    clientactivite  = models.ForeignKey(Activitie,on_delete=models.CASCADE)
    created         = models.DateField(auto_now_add=True)
    user            = models.ForeignKey(User,on_delete=models.CASCADE)

    def __str__(self) :
        return self.clientraison

