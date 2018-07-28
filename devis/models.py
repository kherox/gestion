from django.db import models

# Create your models here.

from clients.models  import Client
from projects.models import Project

"""
    client         = models.ForeignKey(Client,on_delete=models.PROTECT)
    personneabout  = models.CharField(max_length=150,verbose_name="A l'attention de  : ")
    nomproforma    = models.CharField(max_length=150,verbose_name="Nom de la proforma     :")

"""


class Devi(models.Model) :
    project        = models.ForeignKey(Project,on_delete=models.PROTECT)
    numeroproforma = models.CharField(max_length=150,verbose_name="Numero de la proforma : ")
    delailivraison = models.CharField(max_length=150,verbose_name="Delai de livraison : ")
    finvalidate    = models.DateField(verbose_name="Date de fin de validité : ")
    datedemande    = models.DateField(max_length=150,verbose_name="Date de demande : ")
    avance         = models.IntegerField(verbose_name="Avance en % : ")

    def __str__(self):
        return self.project.name
