package com.markjmind.propose.extension;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class UniRecyclerAdapter extends RecyclerView.Adapter<UniViewHolder<?, ?>>{
    protected Store buliderStore = new Store();
    protected ArrayList<Class> holderList = new ArrayList<>();
    final static String emptyHolderGroup = "uni_recycler_adapter_empty_hodler";
    private GroupBuilder emptyGroupBuilder = null;
    protected RecyclerView recyclerView;
    private ScrollDetector scrollDetector;

    public UniRecyclerAdapter(@NotNull RecyclerView recyclerView) {
        scrollDetector = new ScrollDetector(buliderStore);
        initRecyclerView(recyclerView);
    }

    public UniRecyclerAdapter(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.LayoutManager layoutManager) {
        scrollDetector = new ScrollDetector(buliderStore);
        initRecyclerView(recyclerView, layoutManager);
    }

    public UniRecyclerAdapter initRecyclerView(@NotNull RecyclerView recyclerView) {
        return initRecyclerView(recyclerView, new LinearLayoutManager(recyclerView.getContext()));
    }

    public UniRecyclerAdapter initRecyclerView(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.LayoutManager layoutManager) {
        this.recyclerView = recyclerView;
        recyclerView.setAdapter(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(scrollDetector);
        return this;
    }
    public void setOnLastScrollListener(String groupName, OnLastScrollListener listener){
        scrollDetector.setOnLastScrollListener(groupName, listener);
    }

    public void setOnLastScrollListener(int index, OnLastScrollListener listener){
        scrollDetector.setOnLastScrollListener(""+index, listener);
    }

    public void setOnLastScrollListener(OnLastScrollListener listener){
        scrollDetector.setOnLastScrollListener(null, listener);
    }

    @NotNull
    public RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    @NonNull
    @Override
    public UniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return (UniViewHolder) getInstance(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull UniViewHolder holder, int position) {
        holder.setItem(getItem(position));
        holder.onPre();
    }

    public GroupBuilder getGroup(String groupName){
        if(isEmptyShow()){
            return emptyGroupBuilder;
        }
        return ((GroupBuilder)buliderStore.get(groupName));
    }

    private Object getInstance(ViewGroup parents, int viewType){
        try {
            LayoutInflater inflater = LayoutInflater.from(parents.getContext());
            Class<?> targetClass;
            if(viewType < 0){
                if(emptyGroupBuilder == null){
                    throw new RuntimeException("등록된 Holder class가 없습니다.");
                }
                targetClass = emptyGroupBuilder.getHolderClass();
            }else{
                targetClass = holderList.get(viewType);
            }
            Class<?> genericClass = (Class<?>)((ParameterizedType)targetClass.getGenericSuperclass()).getActualTypeArguments()[1];
            Method method = genericClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            ViewDataBinding vb = (ViewDataBinding)method.invoke(null, inflater, parents, false);

            UniViewHolder holder = (UniViewHolder)targetClass.getConstructor(View.class).newInstance(vb.getRoot());
            holder.setBinder(vb);
            holder.setViewType(viewType);
            return holder;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException("HolderTypeAdapter Annotation Exception:1",e);
        }
    }

    @NotNull
    @Override
    public int getItemCount() {
        int totalSize = getItemSize();
        if(isEmptyShow()){
            return 1;
        }else{
            return totalSize;
        }
    }

    public int getItemSize(){
        Object[] values = buliderStore.getValues();
        if(values == null){
            return 0;
        }
        int totalSize = 0;
        for(Object value : values){
            totalSize +=  ((GroupBuilder)value).getList().size();
        }
        return totalSize;
    }

    private boolean isEmptyShow(){
        return getItemSize() == 0 && emptyGroupBuilder != null;
    }

    @NotNull
    @Override
    public int getItemViewType(int position) {
        String[] keys = buliderStore.getKeys();
        int addSize = 0;
        for(String key : keys){
            GroupBuilder builder = (GroupBuilder) buliderStore.get(key);
            if(position < addSize+builder.getList().size()){
                Object item = builder.getList().get(position-addSize);
                Class<? extends UniViewHolder<?, ?>> type = builder.getTypeListener().getType(item, position-addSize, builder.getList());
                builder.setHolderClass(type);
                if(!holderList.contains(type)){
                    holderList.add(type);
                }
                return holderList.indexOf(type);
            }
            addSize += builder.getList().size();
        }
        return -1;
    }

    private GroupBuilder getBuilder(Class holderType){
        Object[] builders = buliderStore.getValues();
        for(Object builder : builders){
            if(((GroupBuilder)builder).getHolderClass().toString().equals(holderType.toString())){
                return (GroupBuilder)builder;
            }
        }
        return null;
    }

    public boolean hasGroup(String groupName){
        return buliderStore.containsKey(groupName);
    }

    public void removeGroup(String groupName){
        if(hasGroup(groupName)){
            int size = getListItem(groupName).size();
            int start = getStartGroupPosition(groupName);
            buliderStore.remove(groupName);
            notifyItemRangeRemoved(start, size);
        }
    }

    public void removeGroup(int index){
        this.removeGroup(""+index);
    }

    public int getGroupIndex(String groupName){
        int index = 0;
        for(String key: buliderStore.getKeys()){
            if(groupName.equals(key)){
                return index;
            }
            index++;
        }
        return index;
    }

    private Object getItem(int position){
        if(isEmptyShow()){
            return emptyGroupBuilder.getList().get(0);
        }else {
            String[] keys = buliderStore.getKeys();
            int addSize = 0;
            for (String key : keys) {
                GroupBuilder builder = (GroupBuilder) buliderStore.get(key);
                if (position < addSize + builder.getList().size()) {
                    return builder.getList().get(position - addSize);
                }
                addSize += builder.getList().size();
            }
        }
        return null;
    }

    int getItemPosition(int adapterPosition) {
        if (isEmptyShow()) {
            return 0;
        } else {
            String[] keys = buliderStore.getKeys();
            int addSize = 0;
            for (String key : keys) {
                GroupBuilder builder = (GroupBuilder) buliderStore.get(key);
                if (adapterPosition < addSize + builder.getList().size()) {
                    return adapterPosition - addSize;
                }
                addSize += builder.getList().size();
            }
            return 0;
        }
    }

    public List getListItem(String groupName){
        return getGroup(groupName).getList();
    }

    public List getListItem(int index){
        return getListItem(""+index);
    }

    public List getListItem(){
        return getListItem(0);
    }

    public Object getSingleItem(int index){
        return getListItem(index).get(0);
    }

    public Object getSingleItem(){
        return getSingleItem(0);
    }



    public GroupBuilder addListItem(@NotNull String groupName, List list, @NotNull TypeListener<?> typeListener){
        if(list == null){
            list = new ArrayList();
        }
        GroupBuilder builder = new GroupBuilder(groupName, list, typeListener);
        buliderStore.add(groupName, builder);
        return builder;
    }

    public GroupBuilder addListItem(List list, @NotNull TypeListener<?> typeListener){
        String groupName = ""+buliderStore.size();
        return this.addListItem(groupName, list, typeListener);
    }


    public GroupBuilder addListItem(@NotNull String groupName, List list, @NotNull Class<? extends UniViewHolder<?, ?>> holderClass){
        return this.addListItem(groupName, list, new SingleTypeListener(holderClass));
    }

    public GroupBuilder addListItem(List list, @NotNull Class<? extends UniViewHolder<?, ?>> holderClass){
        String groupName = ""+buliderStore.size();
        return this.addListItem(groupName, list, holderClass);
    }

    public GroupBuilder addSingleItem(@NotNull String groupName, Object item, @NotNull Class<? extends UniViewHolder<?, ?>> holderClass){
        ArrayList list = new ArrayList();
        list.add(item);
        return this.addListItem(groupName, list, holderClass);
    }

    public GroupBuilder addSingleItem(Object item, @NotNull Class<? extends UniViewHolder<?, ?>> holderClass){
        String groupName = ""+buliderStore.size();
        return this.addSingleItem(groupName, item, holderClass);
    }

    public GroupBuilder setEmptyItem(Object item, @NotNull Class<? extends UniViewHolder<?, ?>> holderClass){
        ArrayList list = new ArrayList();
        list.add(item);
        this.emptyGroupBuilder = new GroupBuilder(emptyHolderGroup, list, new SingleTypeListener(holderClass));
        this.emptyGroupBuilder.setHolderClass(holderClass);
        return this.emptyGroupBuilder;
    }

    public GroupBuilder setEmptyItem(int layoutId){
        return setEmptyItem(null, DefaultEmptyHolder.class).setEmptyLayout(layoutId);
    }

    private class SingleTypeListener implements TypeListener{
        Class<? extends UniViewHolder<?, ?>> holderClass;
        SingleTypeListener(Class<? extends UniViewHolder<?, ?>> holderClass) {
            this.holderClass = holderClass;
        }

        @Override
        public Class<? extends UniViewHolder<?, ?>> getType(@NotNull Object item, @NotNull int position, @NotNull List list) {
            return holderClass;
        }
    }

    public int getStartGroupPosition(String groupName){
        int totalCount = 0;

        for(String key : buliderStore.getKeys()){
            if(key.equals(groupName)){
                break;
            }
            totalCount += ((GroupBuilder)buliderStore.get(key)).getList().size();
        }
        return totalCount;
    }


    private class ScrollDetector extends RecyclerView.OnScrollListener{
        private Store buliderStore;
        private OnLastScrollListener listener;
        private String groupName = null;

        ScrollDetector(Store buliderStore){
            this.buliderStore = buliderStore;
        }

        private int count(){
            int totalCount = 0;
            for(Object b : buliderStore.getValues()){
                totalCount += ((GroupBuilder)b).getList().size();
            }
            return totalCount;
        }
        void setOnLastScrollListener(String groupName, OnLastScrollListener listener){
            this.listener = listener;
            this.groupName = groupName;
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
//            Log.e("dd", "last:"+((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition()+" count:"+count());
            if(listener != null && lastPosition >= count()-1 && buliderStore.size() > 0){
                Object[] values = buliderStore.getValues();
                if(values.length > 0){
                    List list;
                    if(groupName == null){
                        list = ((GroupBuilder)values[values.length-1]).getList();
                    }else{
                        list = getListItem(groupName);
                    }
                    if(list!=null && list.size() > 0){
                        onLast(list.get(list.size()-1), list);
                        return;

                    }
                }
                onLast(null, new ArrayList());
            }
        }

        public void onLast(Object item, List<?> itemList)
        {
            listener.onLast(item, itemList);

        }
    }

    public interface OnLastScrollListener<ItemType>{
        boolean onLast(ItemType item, @NotNull List<ItemType> itemList);
    }

    public class GroupBuilder{
        private Store param = new Store();
        private String groupName;
        private List list;
        private Class<? extends UniViewHolder<?, ?>> holderClass;
        private TypeListener<?> typeListener;
        private View emptyView;

        public GroupBuilder(String groupName, List list, TypeListener<?> typeListener){
            this.groupName = groupName;
            this.list = list;
            this.typeListener = typeListener;
        }

        GroupBuilder setEmptyView(View view){
            this.emptyView = view;
            return this;
        }

        public int layout;
        GroupBuilder setEmptyLayout(int layout){
            this.layout = layout;
            return this;
        }

        Store getParam() {
            return param;
        }

        String getGroupName() {
            return groupName;
        }

        List getList() {
            return list;
        }

        void setList(List list) {
            this.list = list;
        }

        Class<? extends UniViewHolder<?, ?>> getHolderClass() {
            return holderClass;
        }

        void setHolderClass(Class<? extends UniViewHolder<?, ?>> holderClass) {
            this.holderClass = holderClass;
        }

        TypeListener getTypeListener() {
            return typeListener;
        }

        public GroupBuilder addParam(String key, Object value){
            param.add(key, value);
            return this;
        }

    }

    public interface TypeListener<ItemType>{
        Class<? extends UniViewHolder<?,?>> getType(@NotNull ItemType item, @NotNull int position, @NotNull List<?> list);
    }

}