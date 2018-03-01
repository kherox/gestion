from django.shortcuts import render
from django.http import HttpResponse 
import json


from .models import devis_config
from projects.models            import  Project 
from produits.models            import  Product 
from besoins.models import Besoin 

# from projects.models            import  Project 
# from besoins.models             import  Besoin
# from projecttransports.models   import  ProjectTransports 
# from projectmaindoeuvres.models import  ProjectMainDoeuvre
# from projectoutils.models       import  ProjectOutil


"""

    project    = Project.objects.get(id=id)
    besoins    = Besoin.objects.filter(project=project).all()
    outils     = ProjectOutil.objects.filter(project=project).all()
    mainoeuvre = ProjectMainDoeuvre.objects.filter(project=project).all()
    transport  = ProjectTransports.objects.filter(project=project).all()
"""

# Create your views here.

def updateinformations(request):
    params     = request.POST.get("data")
    data       = json.loads(params)

    compact    = data[0].get("compact")
    content    = data[1].get("content")
    projectid  = data[2].get("projectid") 
    project   = Project.objects.get(id=int(projectid))
    content    = content.split(";")
    for item in content:
        val = item.split("_")
        try:
            product = Besoin.objects.get(product_id=int(val[2]))
            devis_config.objects.create(project=project,product=product,compact=compact,parent=val[1])
        except Exception as identifier:
            pass
    
    return  HttpResponse('None')
