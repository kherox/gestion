from django.db import models

from produits.models import Product
from projects.models import Project

# Create your models here.



class Besoin(models.Model) :
    product           = models.ForeignKey(Product,on_delete=models.PROTECT)
    quantity          = models.IntegerField(verbose_name="Le quantité")
    project           = models.ForeignKey(Project,on_delete=models.PROTECT)
    price             = models.IntegerField(verbose_name="Le Prix")
    marge             = models.FloatField(verbose_name=None,blank=False,null=True,default=1.0,editable=True)
    remise            = models.FloatField(verbose_name=None,blank=False,null=True,default=1.0,editable=True)