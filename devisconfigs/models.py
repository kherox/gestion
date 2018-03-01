# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.

from projects.models import Project


class DevisConfig(models.Model) :
    maindoeuvre       = models.BooleanField(default=False)
    transport         = models.BooleanField(default=False)
    created           = models.DateField(auto_now_add=True)
    project           = models.ForeignKey(Project,on_delete=models.PROTECT)



