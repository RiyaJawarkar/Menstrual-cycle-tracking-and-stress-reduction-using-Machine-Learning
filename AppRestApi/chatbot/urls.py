from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import ChatView, WelcomeView, ChatSessionViewSet, health_check,ChatBoxView

router = DefaultRouter()
router.register(r'sessions', ChatSessionViewSet)

urlpatterns = [
    path('', include(router.urls)),
    path('chat/', ChatView.as_view(), name='chat'),
    path('welcome/', WelcomeView.as_view(), name='welcome'),
    path('health/', health_check, name='health_check'),
    path('chatview/',ChatBoxView.as_view())
]

