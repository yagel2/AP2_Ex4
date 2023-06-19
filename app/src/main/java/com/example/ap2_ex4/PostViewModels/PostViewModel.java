//package com.example.ap2_ex4.PostViewModels;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.example.ap2_ex4.contacts.SingleContactInList;
//
//import java.util.List;
//
//public class PostsViewModel extends ViewModel {
//
//        private PostsRepository repository;
//
//        private LiveData<List<SingleContactInList>> posts;
//
//        public PostsViewModel () {
//         repository = new PostsRepository();
//         posts = repository.getAll();
//         }
//
//         public LiveData<List<SingleContactInList>> get() { return posts; }
//
//         public void add(SingleContactInList post) { repository.add(post); }
//
//         public void delete(SingleContactInList post) { repository.delete(post); }
//
//         public void reload() { repository.reload(); }
// }