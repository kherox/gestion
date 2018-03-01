from django.db import models
from fournisseurs.models import Fournisseur
from materiels.models import Materiel
# Create your models here.

class Product(models.Model) :
    reference     = models.CharField(max_length=25)
    name          = models.CharField(max_length=25)
    quantity      = models.IntegerField()
    price         = models.IntegerField()
    fournisseur   = models.ForeignKey(Fournisseur,on_delete=models.CASCADE)
    type_materiel = models.ForeignKey(Materiel,on_delete=models.CASCADE)
    online        = models.BooleanField()
    created       = models.DateField(auto_now_add=True)


    
    class Meta:
        verbose_name = "Article"



    def __str__(self) :
        return self.name