# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.

from clients.models import Client


class ContactClient(models.Model) :
    name       = models.CharField(max_length=150)
    fonction   = models.CharField(max_length=150)
    telephone  = models.CharField(max_length=15)
    mobile     = models.CharField(max_length=15)
    email      = models.EmailField(max_length=150)
    client     = models.ForeignKey(Client,on_delete=models.PROTECT)


    def __str__(self):
        return self.name
