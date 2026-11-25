package collection;

import model.BloodRequest;

import java.util.LinkedList;

/**
 * ============================================================================
 * CLASS: RequestListManager
 * SYLLABUS TOPIC: Unit 2 - Collections Framework (LinkedList)
 * ============================================================================
 *
 * Uses LinkedList<BloodRequest> to maintain blood requests in processing order:
 *   - CRITICAL / HIGH urgency requests are added to the FRONT (addFirst)
 *     so they are prioritized first.
 *   - NORMAL urgency requests are added to the BACK (addLast) in FIFO order.
 */
public class RequestListManager
{
    private final LinkedList<BloodRequest> requestQueue;

    public RequestListManager()
    {
        this.requestQueue = new LinkedList<BloodRequest>();
    }

    public void addRequest(BloodRequest request)
    {
        if (request == null)
        {
            return;
        }
        if (request.isUrgent())
        {
            requestQueue.addFirst(request);
        }
        else
        {
            requestQueue.addLast(request);
        }
    }

    public BloodRequest getNextPendingRequest()
    {
        for (BloodRequest request : requestQueue)
        {
            if ("PENDING".equalsIgnoreCase(request.getStatus()))
            {
                return request;
            }
        }
        return null;
    }

    public BloodRequest findByRequestId(String requestId)
    {
        if (requestId == null)
        {
            return null;
        }
        String target = requestId.trim();
        for (BloodRequest request : requestQueue)
        {
            if (request.getRequestId().equalsIgnoreCase(target))
            {
                return request;
            }
        }
        return null;
    }

    public LinkedList<BloodRequest> getAllRequests()
    {
        return new LinkedList<BloodRequest>(requestQueue);
    }

    public int getTotalRequestCount()
    {
        return requestQueue.size();
    }

    public int getPendingRequestCount()
    {
        int count = 0;
        for (BloodRequest request : requestQueue)
        {
            if ("PENDING".equalsIgnoreCase(request.getStatus()))
            {
                count++;
            }
        }
        return count;
    }
}
