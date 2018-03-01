# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

from fournisseurs.models import Fournisseur

# Create your models here.

class FournisseurContact(models.Model) :
    name        = models.CharField(max_length=150)
    fonction    = models.CharField(max_length=50)
    cel         = models.CharField(max_length=20)
    email       = models.CharField(max_length=50)
    fournisseur = models.ForeignKey(Fournisseur,on_delete=models.PROTECT)


    def __str__(self) :
        return self.name