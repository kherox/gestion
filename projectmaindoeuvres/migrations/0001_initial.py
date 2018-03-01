# -*- coding: utf-8 -*-
# Generated by Django 2.0 on 2018-01-15 08:57

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('mainoeuvres', '0001_initial'),
        ('projects', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='ProjectMainDoeuvre',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('tauxhoraire', models.IntegerField(editable=False, null=True, verbose_name='Taux Horaire')),
                ('nombreheure', models.IntegerField(verbose_name="Nombre d'Heure")),
                ('coefficient', models.IntegerField(verbose_name="Coefficient d'ajustement")),
                ('nombrepersonnes', models.IntegerField(verbose_name='Nombre de personnes')),
                ('montant', models.IntegerField(blank=True, null=True, verbose_name='Montant')),
                ('mainoeuvre', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='mainoeuvres.MainOeuvre', verbose_name='Specialité')),
                ('project', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='projects.Project')),
            ],
        ),
    ]