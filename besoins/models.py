# -*- coding: utf-8 -*-
from django.db import models

from produits.models  import Product
from projects.models  import Project
from rubriques.models import Rubrique

# Create your models here.



class Besoin(models.Model) :
    product           = models.ForeignKey(Product,on_delete=models.PROTECT)
    quantity          = models.IntegerField(verbose_name="Le quantité")
    project           = models.ForeignKey(Project,on_delete=models.PROTECT)
    rubrique          = models.ForeignKey(Rubrique,on_delete=models.PROTECT,default=None)
    price             = models.IntegerField(verbose_name="Le Prix")
    marge             = models.FloatField(verbose_name=None,blank=False,null=True,default=0,editable=True)
    remise            = models.FloatField(verbose_name=None,blank=False,null=True,default=0,editable=True)



    def __str__(self) :
        return self.product.name