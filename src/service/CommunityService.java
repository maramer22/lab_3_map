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

        userService.getAllUsers().forEach(user -> graph.put(user.getID(), new ArrayList<>()));

        friendshipService.getAllFriendships().forEach(f -> {
            Long u1 = f.getIdUser1();
            Long u2 = f.getIdUser2();
            if (graph.containsKey(u1) && graph.containsKey(u2)) {
                graph.get(u1).add(u2);
                graph.get(u2).add(u1);
            }
        });

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
        graph.get(node).stream()
                .filter(neighbor -> !visited.contains(neighbor))
                .forEach(neighbor -> dfs(neighbor, visited, graph, currentCommunity));
    }


    /**
     * Calculates the number of separate communities (connected components) in the network
     *
     * @return the total number of communities
     */
    public int getNumberOfCommunities() {
        return getAllCommunities().size();
    }

    /**
     * Retrieves all separate communities in the network
     *
     * @return a list containing lists of user IDs, where each inner list represents a community
     */
    private List<List<Long>> getAllCommunities() {
        Map<Long, List<Long>> graph = buildGraph();
        Set<Long> visited = new HashSet<>();

        return graph.keySet().stream()
                .filter(userId -> !visited.contains(userId))
                .map(userId -> {
                    List<Long> currentCommunity = new ArrayList<>();
                    dfs(userId, visited, graph, currentCommunity);
                    return currentCommunity;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Finds the most sociable community, defined as the component with the longest path
     *
     * @return a list of user IDs representing the users in the most sociable community
     */
    public List<Long> getMostSociableCommunity() {
        Map<Long, List<Long>> graph = buildGraph();
        return getAllCommunities().stream()
                .flatMap(Collection::stream) // REFERINTA LA METODA
                .map(startNode -> {
                    List<Long> currentMaxPath = new ArrayList<>();
                    dfsLongestPath(startNode, new HashSet<>(), graph, new ArrayList<>(), currentMaxPath);
                    return currentMaxPath;
                })
                .max(Comparator.comparingInt(List::size)) // REFERINTA LA METODA
                .orElse(new ArrayList<>());
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

        graph.get(current).stream()
                .filter(neighbor -> !visited.contains(neighbor))
                .forEach(neighbor -> dfsLongestPath(neighbor, visited, graph, currentPath, maxPath));
        visited.remove(current);
        currentPath.remove(currentPath.size() - 1);
    }
}