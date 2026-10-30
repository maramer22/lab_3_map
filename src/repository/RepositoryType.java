package repository;
/**
 * enumeration of the available repository types
 * used by the RepositoryFactory to determine which implementation to instantiate
 */
public enum RepositoryType {
    MEMORY,
    FILE,
    DATABASE
}
