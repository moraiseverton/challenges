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

    active = models.BooleanField(default=True)

    def __str__(self):
        return self.name


class WorkOrder(models.Model):
    STATE_CHOICES = (
        ("NEW", "New"),
        ("STARTED", "Started"),
        ("COMPLETED", "Completed"),
        ("CANCELLED", "Cancelled"),
    )

    title = models.CharField(
        "Title",
        max_length=250,
        blank=False
    )

    description = models.TextField(
        "Description",
        blank=True,
        null=True
    )

    status = models.CharField(
        "Current status",
        max_length=10,
        choices=STATE_CHOICES,
        blank=False,
        default="NEW"
    )

    facility_id = models.ForeignKey(
        Facility,
        on_delete=models.CASCADE,
        verbose_name="Facility"
    )

    def __str__(self):
        return self.title
