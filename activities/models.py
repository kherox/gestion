from django.db import models

# Create your models here.

class Activitie(models.Model) :
    name         = models.CharField(max_length=150)
    abreviation  = models.CharField(max_length=5)
    created      = models.DateField(auto_now_add=True)

    def __str__(self) :
        return self.name 