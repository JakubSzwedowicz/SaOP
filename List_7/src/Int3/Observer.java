package Int3;

public interface Observer {
    public default boolean update(int a_changeFreeSpace){
        return update(a_changeFreeSpace, 0);
    };
    public boolean update(int a_changeFreeSpace, int a_changeCapacity);
}
