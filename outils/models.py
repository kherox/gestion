from django.db import models

# Create your models here.

class Outil(models.Model) :
    name      = models.CharField(max_length=50)
    price     = models.IntegerField(verbose_name="Cout de mise a disposition")
    created   = models.DateField(auto_now_add=True)



    def __str__(self) :
        return self.name 
