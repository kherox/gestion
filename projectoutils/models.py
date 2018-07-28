from django.db import models

# Create your models here.

from outils.models import Outil
from projects.models import Project

class ProjectOutil(models.Model) :
    outil         = models.ForeignKey(Outil,on_delete=models.PROTECT)
    valeur        = models.IntegerField(verbose_name="Valeur",editable=False)
    quantite      = models.IntegerField(verbose_name="Quantité necessaire")
    duree         = models.IntegerField(verbose_name="Nombre de jours")
    montant       = models.IntegerField(verbose_name="Montant",null=True,blank=True)
    project       = models.ForeignKey(Project,on_delete=models.PROTECT)

    def __str__(self) :
        return self.outil.name

from django.db.models import signals

def projectoutils_pre_save(signal,instance,sender,**kwargs) :
    if instance.outil :
        val        = getattr(instance.outil,"price",None)
        if val is not None :
            instance.valeur = int(val)
            montant         = instance.valeur * instance.quantite  * instance.duree 
            instance.montant=montant

signals.pre_save.connect(projectoutils_pre_save,sender=ProjectOutil)