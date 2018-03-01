# Generated by Django 2.0 on 2018-01-15 08:57

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        ('produits', '0001_initial'),
        ('besoins', '0001_initial'),
        ('projects', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='besoin',
            name='product',
            field=models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='produits.Product'),
        ),
        migrations.AddField(
            model_name='besoin',
            name='project',
            field=models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='projects.Project'),
        ),
    ]