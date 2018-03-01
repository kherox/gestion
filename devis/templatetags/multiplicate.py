# -*- coding: utf-8 -*-

from django import template
import math

register = template.Library()

@register.filter(name="somme")
def somme(value,args) :
    if value > 0:
        return value + args
    else :
        return args

@register.filter(name='multiple')
def multiple(value,arg) :
    return value * arg

@register.filter(name="remise")
def remise(value,arg):
    rs    = math.ceil(value * math.ceil(arg/100))
    return rs 



@register.filter(name='solde')
def solde(value) :
    return 100 - value

@register.filter(name='outiltotal')
def outiltotal(values) :
    montant =  0 
    for val in values :
        montant = montant + int(val.montant)
    return montant

@register.filter(name='transportotal')
def transportotal(values) :
    montant =  0 
    for val in values :
        montant = montant + int(val.montant)
    return montant

@register.filter(name='mainoeuvretotal')
def mainoeuvretotal(values) :
    montant =  0 
    for val in values :
        montant = montant + int(val.montant)
    return montant


@register.filter(name='tva')
def tva(values) :
    return values * 0.18 


@register.filter(name='escompte')
def escompte(values,avance) :
    """
     ON calcul les escompte a partir des l'avance et du montant global
    """
    escompte = (values + (values * 0.18)) - (values - (values * avance))
    return escompte


@register.filter(name='acompte')
def acompte(values) :
    return values

@register.filter(name='ttc')
def ttc(values) :
    # a calculer
    tva = values * 0.18 
    return values + tva 

@register.filter(name="htax")
def htax(values):
    return values

@register.filter(name="getparent")
def getparent(args,values):
    return values

@register.filter(name='total')
def total(besoins,transport,maindoeuvre,outils) :
    total = 0
    if besoins :
        for besoin in besoins :
            montant = besoin.price * besoin.quantity
            total   = total + montant
    if transport :
        montant =  0
        for trans in transport :
            montant = montant + trans.montant
        total += montant
    
    if maindoeuvre :
        montant = 0
        for mdo in maindoeuvre :
            montant = montant + mdo.montant
        total += montant
    if outils :
        montant = 0
        for otl in outils :
            montant = montant + otl.montant
        total += montant
    return total
            

            

"""
Pour la logique applicative
<td style="border-left: 1px solid #000000; 
border-right: 1px solid #000000" colspan=1 height="22" 
align="left" valign=middle sdval="5" sdnum="1033;">
<font color="#000000"></font></td>

{{besoin.product.price|multiple:besoin.product.quantity }}

"""
@register.filter(name='returnCustomTag')
def returnCustomTag(args,values) :
    parent  = ""
    content = " "
    body    = " "
    

    for besoin in values :
        value = besoin.product
        price  =  int(besoin.price) * int(besoin.quantity)
        if parent != besoin.rubrique.name :
            parent = besoin.rubrique.name
            content = content + '<tr class="classmain"><td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=1 height="22" align="left" valign=middle sdval="5" sdnum="1033;"><font color="#000000"></font></td>' + \
                    '<td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=14 align="left" valign=middle><font color="#000000">'+parent.upper()+'</font></td>'+\
                    '<td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=1 align="center" valign=middle sdval="1" sdnum="1033;"><font color="#000000"></font></td>'+\
                            '<td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=2 align="right" valign=middle sdval="126937.5" sdnum="1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-"><font color="#000000"></font></td>'+\
                            '<td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=1 align="center" valign=middle sdval="0.1" sdnum="1033;0;0%"><font color="#000000"></font></td>'+\
                            '<td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=4 align="right" valign=middle sdval="126937.5" sdnum="1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-"><font color="#000000"></font></td>'+\
                            '<td align="left" valign=bottom><font color="#000000"><br/></font></td>'+\
                            '<td align="left" valign=bottom><font color="#000000"><br/></font></td>'+\
                            '<td align="left" valign=bottom><font color="#000000"><br/></font></td>'+\
                            '<td align="left" valign=bottom><font color="#000000"><br/></font></td></tr>'   

        content = content + '<tr class="classmain"><td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=1 height="22" align="left" valign=middle sdval="5" sdnum="1033;"><font color="#000000">'+value.reference+'</font></td>' + \
                    '<td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=14 align="left" valign=middle><font color="#000000">'+value.name+'</font></td>'+ \
                    '<td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=1 align="center" valign=middle sdval="1" sdnum="1033;"><font color="#000000">'+str(besoin.quantity)+'</font></td>'+ \
                    '<td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=2 align="right" valign=middle sdval="126937.5" sdnum="1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-"><font color="#000000">'+str(besoin.price)+'</font></td>'+ \
                    '<td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=1 align="center" valign=middle sdval="0.1" sdnum="1033;0;0%"><font color="#000000">'+str(besoin.remise)+'</font></td>'+ \
                    '<td style="border-left: 1px solid #000000; border-right: 1px solid #000000" colspan=4 align="right" valign=middle sdval="126937.5" sdnum="1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-"><font color="#000000">'+str(price)+'</font></td>'+ \
                    '<td align="left" valign=bottom><font color="#000000"><br/></font></td>'+\
                    '<td align="left" valign=bottom><font color="#000000"><br/></font></td>'+\
                    '<td align="left" valign=bottom><font color="#000000"><br/></font></td>'+\
                    '<td align="left" valign=bottom><font color="#000000"><br/></font></td></tr>'
 
    return content
