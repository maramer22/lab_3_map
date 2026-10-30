package service;

import domain.Friendship;
import domain.User;

import java.util.*;

/**
 * Service class responsible for complex network analysis
 * Uses graph algorithms to determine communities (connected components)
 * and sociability metrics within the social network.
 */
public class CommunityService {
    private UserService userService;
    private FriendshipService friendshipService;

    /**
     * Initializes the CommunityService with necessary sub-services
     *
     * @param userService       the service for managing users
     * @param friendshipService the service for managing friendships
     */
    public CommunityService(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    /**
     * Builds an adjacency list representing the undirected graph of the social network
     *
     * @return a map where each key is a user ID and the value is a list of their friends' IDs
     */
    private Map<Long, List<Long>> buildGraph() {
        Map<Long, List<Long>> graph = new HashMap<>();

        for (User user : userService.getAllUsers()) {
            graph.put(user.getID(), new ArrayList<>());
        }

        for (Friendship f : friendshipService.getAllFriendships()) {
            Long u1 = f.getIdUser1();
            Long u2 = f.getIdUser2();

            // Graful este neorientat
            if (graph.containsKey(u1) && graph.containsKey(u2)) {
                graph.get(u1).add(u2);
                graph.get(u2).add(u1);
            }
        }
        return graph;
    }

    /**
     * Helper method to perform a Depth First Search (DFS) for finding connected components
     */
    private void dfs(Long node, Set<Long> visited, Map<Long, List<Long>> graph, List<Long> currentCommunity) {
        visited.add(node);
        if (currentCommunity != null) {
            currentCommunity.add(node);
        }
        for (Long neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited, graph, currentCommunity);
            }
        }
    }

    /**
     * Calculates the number of separate communities (connected components) in the network
     *
     * @return the total number of communities
     */
    public int getNumberOfCommunities() {
        Map<Long, List<Long>> graph = buildGraph();
        Set<Long> visited = new HashSet<>();
        int count = 0;

        for (Long userId : graph.keySet()) {
            if (!visited.contains(userId)) {
                count++;
                dfs(userId, visited, graph, null);
            }
        }
        return count;
    }

    /**
     * Retrieves all separate communities in the network
     *
     * @return a list containing lists of user IDs, where each inner list represents a community
     */
    private List<List<Long>> getAllCommunities() {
        Map<Long, List<Long>> graph = buildGraph();
        Set<Long> visited = new HashSet<>();
        List<List<Long>> communities = new ArrayList<>();

        for (Long userId : graph.keySet()) {
            if (!visited.contains(userId)) {
                List<Long> currentCommunity = new ArrayList<>();
                dfs(userId, visited, graph, currentCommunity);
                communities.add(currentCommunity);
            }
        }
        return communities;
    }

    /**
     * Finds the most sociable community, defined as the component with the longest path
     *
     * @return a list of user IDs representing the users in the most sociable community
     */
    public List<Long> getMostSociableCommunity() {
        Map<Long, List<Long>> graph = buildGraph();
        List<List<Long>> communities = getAllCommunities();

        List<Long> mostSociableCommunity = new ArrayList<>();
        int maxPathLength = -1;

        for (List<Long> community : communities) {
            for (Long startNode : community) {
                List<Long> currentMaxPath = new ArrayList<>();
                dfsLongestPath(startNode, new HashSet<>(), graph, new ArrayList<>(), currentMaxPath);

                if (currentMaxPath.size() > maxPathLength) {
                    maxPathLength = currentMaxPath.size();
                    mostSociableCommunity = currentMaxPath;
                }
            }
        }
        return mostSociableCommunity;
    }

    /**
     * Helper method implementing backtracking DFS to find the longest simple path in a graph component
     */
    private void dfsLongestPath(Long current, Set<Long> visited, Map<Long, List<Long>> graph,
                                List<Long> currentPath, List<Long> maxPath) {
        visited.add(current);
        currentPath.add(current);

        if (currentPath.size() > maxPath.size()) {
            maxPath.clear();
            maxPath.addAll(currentPath);
        }

        for (Long neighbor : graph.get(current)) {
            if (!visited.contains(neighbor)) {
                dfsLongestPath(neighbor, visited, graph, currentPath, maxPath);
            }
        }

        // Backtrack
        visited.remove(current);
        currentPath.remove(currentPath.size() - 1);
    }
}