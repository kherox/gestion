# Generated by Django 2.0 on 2018-01-15 08:57

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='MainOeuvre',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=150)),
                ('qualification', models.CharField(max_length=25)),
                ('montant', models.IntegerField(verbose_name='Montant')),
                ('created', models.DateField(auto_now_add=True)),
            ],
        ),
    ]
