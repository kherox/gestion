from django.db import models

# Create your models here.

"""
    email    = models.EmailField()

"""

class Fournisseur(models.Model) :
    name     = models.CharField(max_length=150,verbose_name="Raison social")
    ville    = models.CharField(max_length=150,verbose_name="Ville")
    contact  = models.CharField(max_length=150,verbose_name="Telephone")
    cel      = models.CharField(max_length=20,verbose_name="Cel",default=None)
    created  = models.DateField(auto_now_add=True)


    def __str__(self) :
        return self.name
