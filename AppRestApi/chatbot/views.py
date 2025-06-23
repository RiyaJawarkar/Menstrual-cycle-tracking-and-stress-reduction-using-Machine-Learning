from django.shortcuts import render
from django.views import View
# Create your views here.
from rest_framework import viewsets, status
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.decorators import api_view
from django.utils import timezone
from datetime import timedelta
from . import myconfig
from .models import ChatSession, Message
from .serializers import (
    ChatSessionSerializer, 
    MessageSerializer,
    ChatMessageRequestSerializer,
    ChatMessageResponseSerializer
)
from .bot import PeriodStressReductionBot

class ChatView(APIView):
    """
    Chat endpoint for the chatbot API.
    """
    def post(self, request):
        # Validate request data
        serializer = ChatMessageRequestSerializer(data=request.data)
        if not serializer.is_valid():
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        
        # Extract data
        session_id = serializer.validated_data.get('session_id')
        user_message = serializer.validated_data.get('message')
        
        session = None
        # Get or create session
        if session_id:
            try:
                session = ChatSession.objects.get(session_id=session_id, is_active=True)
                # Update last interaction time
                session.last_interaction = timezone.now()
                session.save()
            except ChatSession.DoesNotExist:
                # Session not found, create a new one
                session = ChatSession.objects.create()
        else:
            # Create a new session
            session = ChatSession.objects.create()
        
        # Create a bot instance with the user's name
        bot = PeriodStressReductionBot(session.user_name)
        
        # Save user message
        Message.objects.create(
            session=session,
            content=user_message,
            is_bot=False
        )
        
        # Process message with bot
        bot_response = bot.process_message(user_message)
        
        # Update user name if it was set in this interaction
        if bot.user_name != "there" and session.user_name != bot.user_name:
            session.user_name = bot.user_name
            session.save()
        
        # Save bot response
        Message.objects.create(
            session=session,
            content=bot_response['response'],
            is_bot=True
        )
        
        # If session should end, mark it as inactive
        if bot_response['end_session']:
            session.is_active = False
            session.save()
        
        # Prepare response
        response_data = {
            'session_id': session.session_id,
            'response': bot_response['response'],
            'end_session': bot_response['end_session']
        }
        
        response_serializer = ChatMessageResponseSerializer(data=response_data)
        response_serializer.is_valid(raise_exception=True)
        
        return Response(response_serializer.data)


class WelcomeView(APIView):
    """
    Welcome endpoint for new sessions.
    """
    def get(self, request):
        # Create a new session
        session = ChatSession.objects.create()
        
        # Create welcome message
        welcome_message = "🌸 Welcome to the Period Stress Reduction Assistant 🌸\nI'm here to provide support and tips for managing period-related stress. What's your name?"
        
        # Save bot message
        Message.objects.create(
            session=session,
            content=welcome_message,
            is_bot=True
        )
        
        # Prepare response
        response_data = {
            'session_id': session.session_id,
            'response': welcome_message,
            'end_session': False
        }
        
        return Response(response_data)


class ChatSessionViewSet(viewsets.ReadOnlyModelViewSet):
    """
    ViewSet for retrieving chat sessions.
    """
    queryset = ChatSession.objects.all()
    serializer_class = ChatSessionSerializer


@api_view(['GET'])
def health_check(request):
    """
    Health check endpoint.
    """
    # Count active sessions
    active_sessions = ChatSession.objects.filter(is_active=True).count()
    
    # Count sessions from past 24 hours
    recent_sessions = ChatSession.objects.filter(
        created_at__gte=timezone.now() - timedelta(days=1)
    ).count()
    
    return Response({
        'status': 'healthy',
        'active_sessions': active_sessions,
        'recent_sessions': recent_sessions
    })

class ChatBoxView(View):
    def get(self,request):
        dic={'server_ip':myconfig.server_ip}
        return render(request,template_name="index.html",context=dic)
    