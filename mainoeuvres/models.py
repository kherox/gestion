from django.db import models

# Create your models here.

class MainOeuvre(models.Model) :
    name           = models.CharField(max_length=150)
    qualification  = models.CharField(max_length=25)
    montant        = models.IntegerField(verbose_name="Montant")
    created        = models.DateField(auto_now_add=True)
    

    def __str__(self) :
        return self.name 