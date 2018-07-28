# -*- coding: utf-8 -*-
# Generated by Django 1.11 on 2018-02-21 08:05
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('projects', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='DevisConfig',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('maindoeuvre', models.BooleanField(default=False)),
                ('transport', models.BooleanField(default=False)),
                ('created', models.DateField(auto_now_add=True)),
                ('project', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='projects.Project')),
            ],
        ),
    ]