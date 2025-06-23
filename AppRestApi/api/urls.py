from django.urls import path,include
from . import views
urlpatterns = [
    path('test/',views.index),
    path('createuser/',views.CreateUserView.as_view()),
    path('listuser/',views.ListUserView.as_view()),
    path('oneuser/<pk>',views.OneUserView.as_view()),
    path('updateuser/<pk>',views.UpdateUserView.as_view()),
    path('createuserperiod/',views.CreateUserPeriodView.as_view()),
    path('listuserperiod/',views.ListUserPeriodView.as_view()),
    path('predictuserperiod/<id>/',views.getPredictionByUserId)
]