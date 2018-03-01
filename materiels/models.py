from django.db import models

# Create your models here.

class Materiel(models.Model) :
    name     = models.CharField(max_length=150)
    created  = models.DateField(auto_now_add=True)


    def __str__(self) :
        return self.name
    