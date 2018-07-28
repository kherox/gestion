from django.db import models

# Create your models here.

class Transport(models.Model): 
    name     = models.CharField(max_length=150)
    price    = models.IntegerField(verbose_name="Montant")
    created  = models.DateField(auto_now_add=True)


    def __str__(self) :
        return self.name 
