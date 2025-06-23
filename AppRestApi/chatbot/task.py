from django.utils import timezone
from datetime import timedelta
from .models import ChatSession

def cleanup_inactive_sessions():
    """
    Clean up inactive sessions that are older than 24 hours.
    """
    # Get sessions that have been inactive for more than 24 hours
    cutoff_time = timezone.now() - timedelta(hours=24)
    old_sessions = ChatSession.objects.filter(
        last_interaction__lt=cutoff_time,
        is_active=True
    )
    
    # Mark them as inactive
    count = old_sessions.count()
    old_sessions.update(is_active=False)
    
    return f"Cleaned up {count} inactive sessions"