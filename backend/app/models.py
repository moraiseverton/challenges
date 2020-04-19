from django.db import models
from django_countries.fields import CountryField


class Facility(models.Model):

    PROVINCE_CHOICES = (
        ("NL", "Newfoundland and Labrador"),
        ("PE", "Prince Edward Island"),
        ("NS", "Nova Scotia"),
        ("NB", "New Brunswick"),
        ("QC", "Quebec"),
        ("ON", "Ontario"),
        ("MB", "Manitoba"),
        ("SK", "Saskatchewan"),
        ("AB", "Alberta"),
        ("BC", "British Columbia"),
        ("YT", "Yukon"),
        ("NT", "Northwest Territories"),
        ("NU", "Nunavut"),
    )

    class Meta:
        db_table = 'facilities'

    name = models.CharField(
        "Name",
        max_length=100,
        blank=False
    )

    address = models.CharField(
        "Address",
        max_length=300,
        blank=False
    )

    zip_code = models.CharField(
        "ZIP / Postal code",
        max_length=6,
        blank=False
    )

    city = models.CharField(
        "City",
        max_length=100,
        blank=False
    )

    province = models.CharField(
        "Province",
        max_length=100,
        choices=PROVINCE_CHOICES,
        blank=False
    )

    country = CountryField()

    def _str_(self):
        return self.name

