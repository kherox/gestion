# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.

from projects.models import Project

class Rubrique(models.Model) :
    project         = models.ForeignKey(Project,on_delete=models.PROTECT)
    name            = models.CharField(max_length=150)


    def __str__(self) :
        return self.name
    
