import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class Student {
    String name;
    String gender;
    String studentClass;
    String section;
    String rollNumber;
    String grade;
    int priority; // used for priority queue demo

    Student(String name, String gender, String studentClass,
            String section, String rollNumber, String grade, int priority) {
        this.name = name;
        this.gender = gender;
        this.studentClass = studentClass;
        this.section = section;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.priority = priority;
    }

    void display() {
        System.out.println("----------------------------------");
        System.out.println("Name      : " + name);
        System.out.println("Gender    : " + gender);
        System.out.println("Class     : " + studentClass);
        System.out.println("Section   : " + section);
        System.out.println("Roll No   : " + rollNumber);
        System.out.println("Grade     : " + grade);
        System.out.println("Priority  : " + priority);
    }

    @Override
    public String toString() {
        return name + " | " + rollNumber + " | " + grade + " | P:" + priority;
    }
}

class StudentManagementApp {

    static Scanner sc = new Scanner(System.in);

    // Login
    static final String USERNAME = "admin";
    static final String PASSWORD = "1234";

    // CO2/CO5: Array-based ADT
    static ArrayList<Student> students = new ArrayList<>();

    // CO2: linked lists
    static SinglyLinkedList singlyList = new SinglyLinkedList();
    static DoublyLinkedList doublyList = new DoublyLinkedList();
    static CircularLinkedList circularList = new CircularLinkedList();

    // CO3/CO4: Java collections
    static Queue<Student> queue = new LinkedList<>();
    static Deque<Student> deque = new ArrayDeque<>();
    static CircularQueue circularQueue = new CircularQueue(50);
    static PriorityQueue<Student> priorityQueue =
            new PriorityQueue<>(Comparator.comparingInt((Student s) -> s.priority).reversed());

    // CO4: fast lookup
    static Map<String, Student> studentMap = new HashMap<>();
    static ChainingHashTable chainingTable = new ChainingHashTable(10);
    static OpenAddressingHashTable openAddressingTable = new OpenAddressingHashTable(11);

    public static void main(String[] args) {
        if (!login()) {
            System.out.println("Invalid login. Exiting...");
            return;
        }

        while (true) {
            System.out.println("\n===== Student Record Management =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student by Name (Linear Search)");
            System.out.println("4. Search Student by Roll (Binary Search)");
            System.out.println("5. Delete Student");
            System.out.println("6. Sort Students");
            System.out.println("7. Linked List Operations");
            System.out.println("8. Queue / Deque Operations");
            System.out.println("9. Priority Queue Operations");
            System.out.println("10. Hash Search Operations");
            System.out.println("11. Complexity Report");
            System.out.println("12. Exit");
            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudentLinear();
                case 4 -> searchStudentBinary();
                case 5 -> deleteStudent();
                case 6 -> sortMenu();
                case 7 -> linkedListMenu();
                case 8 -> queueMenu();
                case 9 -> priorityQueueMenu();
                case 10 -> hashMenu();
                case 11 -> complexityReport();
                case 12 -> {
                    System.out.println("Exiting program...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static boolean login() {
        System.out.println("===== Login =====");
        System.out.print("Username: ");
        String user = sc.nextLine().trim();
        System.out.print("Password: ");
        String pass = sc.nextLine().trim();
        return USERNAME.equals(user) && PASSWORD.equals(pass);
    }

    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter Gender: ");
        String gender = sc.nextLine().trim();

        System.out.print("Enter Class: ");
        String studentClass = sc.nextLine().trim();

        System.out.print("Enter Section: ");
        String section = sc.nextLine().trim();

        System.out.print("Enter Roll Number: ");
        String rollNumber = sc.nextLine().trim();

        System.out.print("Enter Grade: ");
        String grade = sc.nextLine().trim();

        System.out.print("Enter Priority (1-10): ");
        int priority = readInt();

        if (name.isEmpty()) {
            System.out.println("Student name is required.");
            return;
        }

        Student student = new Student(name, gender, studentClass, section, rollNumber, grade, priority);

        // CO2/CO5
        students.add(student);

        // CO2
        singlyList.insert(student);
        doublyList.insert(student);
        circularList.insert(student);

        // CO3/CO4
        queue.offer(student);
        deque.offerLast(student);
        circularQueue.enqueue(student);
        priorityQueue.offer(student);

        // CO4
        studentMap.put(rollNumber, student);
        chainingTable.put(rollNumber, student);
        openAddressingTable.put(rollNumber, student);

        System.out.println("Student added successfully!");
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }

    // CO1: Linear Search
    static void searchStudentLinear() {
        System.out.print("Enter Name to Search: ");
        String searchName = sc.nextLine().trim();

        boolean found = false;
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(searchName)) {
                s.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }

    // CO1: Binary Search
    static void searchStudentBinary() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        ArrayList<Student> copy = new ArrayList<>(students);
        mergeSort(copy, 0, copy.size() - 1);

        System.out.print("Enter Roll Number to Search: ");
        String roll = sc.nextLine().trim();

        int low = 0, high = copy.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = copy.get(mid).rollNumber.compareToIgnoreCase(roll);

            if (cmp == 0) {
                copy.get(mid).display();
                return;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Student not found.");
    }

    static void deleteStudent() {
        System.out.print("Enter Roll Number to Delete: ");
        String roll = sc.nextLine().trim();

        boolean removed = students.removeIf(s -> s.rollNumber.equalsIgnoreCase(roll));
        singlyList.delete(roll);
        doublyList.delete(roll);
        circularList.delete(roll);

        queue.removeIf(s -> s.rollNumber.equalsIgnoreCase(roll));
        deque.removeIf(s -> s.rollNumber.equalsIgnoreCase(roll));
        circularQueue.remove(roll);
        priorityQueue.removeIf(s -> s.rollNumber.equalsIgnoreCase(roll));

        studentMap.remove(roll);
        chainingTable.remove(roll);
        openAddressingTable.remove(roll);

        if (removed) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found.");
        }
    }

    // CO1 sorting
    static void sortMenu() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        ArrayList<Student> copy = new ArrayList<>(students);

        System.out.println("\n--- Sort Menu ---");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Selection Sort");
        System.out.println("3. Insertion Sort");
        System.out.println("4. Merge Sort");
        System.out.println("5. Quick Sort");
        System.out.print("Choose: ");
        int choice = readInt();

        switch (choice) {
            case 1 -> bubbleSort(copy);
            case 2 -> selectionSort(copy);
            case 3 -> insertionSort(copy);
            case 4 -> mergeSort(copy, 0, copy.size() - 1);
            case 5 -> quickSort(copy, 0, copy.size() - 1);
            default -> {
                System.out.println("Invalid choice.");
                return;
            }
        }

        for (Student s : copy) {
            System.out.println(s);
        }
    }

    static void linkedListMenu() {
        while (true) {
            System.out.println("\n--- Linked List Menu ---");
            System.out.println("1. Traverse Singly Linked List");
            System.out.println("2. Search in Singly Linked List");
            System.out.println("3. Reverse Singly Linked List");
            System.out.println("4. Detect Cycle in Circular Linked List");
            System.out.println("5. Traverse Doubly Linked List Forward");
            System.out.println("6. Traverse Doubly Linked List Backward");
            System.out.println("7. Traverse Circular Linked List");
            System.out.println("8. Back");
            System.out.print("Choose: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> singlyList.traverse();
                case 2 -> {
                    System.out.print("Enter Roll Number: ");
                    Student s = singlyList.search(sc.nextLine().trim());
                    System.out.println(s != null ? s : "Student not found.");
                }
                case 3 -> {
                    singlyList.reverse();
                    System.out.println("Singly linked list reversed.");
                }
                case 4 -> System.out.println("Cycle detected: " + circularList.detectCycle());
                case 5 -> doublyList.traverseForward();
                case 6 -> doublyList.traverseBackward();
                case 7 -> circularList.traverse();
                case 8 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void queueMenu() {
        while (true) {
            System.out.println("\n--- Queue / Deque Menu ---");
            System.out.println("1. Display Queue");
            System.out.println("2. Dequeue from Queue");
            System.out.println("3. Display Circular Queue");
            System.out.println("4. Dequeue from Circular Queue");
            System.out.println("5. Display Deque");
            System.out.println("6. Remove First from Deque");
            System.out.println("7. Remove Last from Deque");
            System.out.println("8. Back");
            System.out.print("Choose: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> displayQueue();
                case 2 -> System.out.println("Dequeued: " + queue.poll());
                case 3 -> circularQueue.display();
                case 4 -> System.out.println("Dequeued: " + circularQueue.dequeue());
                case 5 -> displayDeque();
                case 6 -> System.out.println("Removed First: " + deque.pollFirst());
                case 7 -> System.out.println("Removed Last: " + deque.pollLast());
                case 8 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void priorityQueueMenu() {
        while (true) {
            System.out.println("\n--- Priority Queue Menu ---");
            System.out.println("1. Display Priority Queue");
            System.out.println("2. Process Highest Priority Student");
            System.out.println("3. Back");
            System.out.print("Choose: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> displayPriorityQueue();
                case 2 -> System.out.println("Processed: " + priorityQueue.poll());
                case 3 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void hashMenu() {
        while (true) {
            System.out.println("\n--- Hash Search Menu ---");
            System.out.println("1. Search using HashMap");
            System.out.println("2. Search using Chaining Hash Table");
            System.out.println("3. Search using Open Addressing Hash Table");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Roll Number: ");
                    System.out.println(studentMap.get(sc.nextLine().trim()));
                }
                case 2 -> {
                    System.out.print("Enter Roll Number: ");
                    System.out.println(chainingTable.get(sc.nextLine().trim()));
                }
                case 3 -> {
                    System.out.print("Enter Roll Number: ");
                    System.out.println(openAddressingTable.get(sc.nextLine().trim()));
                }
                case 4 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void complexityReport() {
        System.out.println("\n--- Complexity Report ---");
        System.out.println("Linear Search : O(n), Ω(1), Θ(n)");
        System.out.println("Binary Search : O(log n), Ω(1), Θ(log n)");
        System.out.println("Bubble Sort   : O(n^2), Ω(n), Θ(n^2)");
        System.out.println("Selection Sort: O(n^2), Ω(n^2), Θ(n^2)");
        System.out.println("Insertion Sort: O(n^2), Ω(n), Θ(n^2)");
        System.out.println("Merge Sort    : O(n log n), Ω(n log n), Θ(n log n)");
        System.out.println("Quick Sort    : O(n^2), Ω(n log n), Θ(n log n) average");
        System.out.println("HashMap lookup: Average O(1)");
        System.out.println("Queue/Deque   : O(1) insertion/removal at ends");
    }

    static void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        for (Student s : queue) {
            System.out.println(s);
        }
    }

    static void displayDeque() {
        if (deque.isEmpty()) {
            System.out.println("Deque is empty.");
            return;
        }
        for (Student s : deque) {
            System.out.println(s);
        }
    }

    static void displayPriorityQueue() {
        if (priorityQueue.isEmpty()) {
            System.out.println("Priority queue is empty.");
            return;
        }
        PriorityQueue<Student> temp =
                new PriorityQueue<>(Comparator.comparingInt((Student s) -> s.priority).reversed());
        temp.addAll(priorityQueue);
        while (!temp.isEmpty()) {
            System.out.println(temp.poll());
        }
    }

    // ---------- CO1 sorting implementations ----------
    static void bubbleSort(ArrayList<Student> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).rollNumber.compareToIgnoreCase(list.get(j + 1).rollNumber) > 0) {
                    Student temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    static void selectionSort(ArrayList<Student> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).rollNumber.compareToIgnoreCase(list.get(min).rollNumber) < 0) {
                    min = j;
                }
            }
            Student temp = list.get(i);
            list.set(i, list.get(min));
            list.set(min, temp);
        }
    }

    static void insertionSort(ArrayList<Student> list) {
        for (int i = 1; i < list.size(); i++) {
            Student key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).rollNumber.compareToIgnoreCase(key.rollNumber) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    static void mergeSort(ArrayList<Student> list, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(list, left, mid);
        mergeSort(list, mid + 1, right);
        merge(list, left, mid, right);
    }

    static void merge(ArrayList<Student> list, int left, int mid, int right) {
        ArrayList<Student> temp = new ArrayList<>();
        int i = left;
        int j = mid + 1;

        while (i <= mid && j <= right) {
            if (list.get(i).rollNumber.compareToIgnoreCase(list.get(j).rollNumber) <= 0) {
                temp.add(list.get(i++));
            } else {
                temp.add(list.get(j++));
            }
        }

        while (i <= mid) temp.add(list.get(i++));
        while (j <= right) temp.add(list.get(j++));

        for (int k = 0; k < temp.size(); k++) {
            list.set(left + k, temp.get(k));
        }
    }

    static void quickSort(ArrayList<Student> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    static int partition(ArrayList<Student> list, int low, int high) {
        Student pivot = list.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).rollNumber.compareToIgnoreCase(pivot.rollNumber) < 0) {
                i++;
                Student temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        Student temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }

    // ---------- CO2 linked lists ----------
    static class SinglyLinkedList {
        static class Node {
            Student data;
            Node next;
            Node(Student data) { this.data = data; }
        }

        Node head;

        void insert(Student s) {
            Node n = new Node(s);
            if (head == null) {
                head = n;
                return;
            }
            Node temp = head;
            while (temp.next != null) temp = temp.next;
            temp.next = n;
        }

        void traverse() {
            if (head == null) {
                System.out.println("Singly linked list is empty.");
                return;
            }
            Node temp = head;
            while (temp != null) {
                System.out.println(temp.data);
                temp = temp.next;
            }
        }

        Student search(String roll) {
            Node temp = head;
            while (temp != null) {
                if (temp.data.rollNumber.equalsIgnoreCase(roll)) return temp.data;
                temp = temp.next;
            }
            return null;
        }

        boolean delete(String roll) {
            if (head == null) return false;

            if (head.data.rollNumber.equalsIgnoreCase(roll)) {
                head = head.next;
                return true;
            }

            Node prev = head;
            Node cur = head.next;
            while (cur != null) {
                if (cur.data.rollNumber.equalsIgnoreCase(roll)) {
                    prev.next = cur.next;
                    return true;
                }
                prev = cur;
                cur = cur.next;
            }
            return false;
        }

        void reverse() {
            Node prev = null;
            Node cur = head;
            while (cur != null) {
                Node next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
            }
            head = prev;
        }
    }

    static class DoublyLinkedList {
        static class Node {
            Student data;
            Node prev, next;
            Node(Student data) { this.data = data; }
        }

        Node head, tail;

        void insert(Student s) {
            Node n = new Node(s);
            if (head == null) {
                head = tail = n;
                return;
            }
            tail.next = n;
            n.prev = tail;
            tail = n;
        }

        boolean delete(String roll) {
            Node temp = head;
            while (temp != null) {
                if (temp.data.rollNumber.equalsIgnoreCase(roll)) {
                    if (temp.prev != null) temp.prev.next = temp.next;
                    else head = temp.next;

                    if (temp.next != null) temp.next.prev = temp.prev;
                    else tail = temp.prev;

                    return true;
                }
                temp = temp.next;
            }
            return false;
        }

        void traverseForward() {
            Node temp = head;
            if (temp == null) {
                System.out.println("Doubly linked list is empty.");
                return;
            }
            while (temp != null) {
                System.out.println(temp.data);
                temp = temp.next;
            }
        }

        void traverseBackward() {
            Node temp = tail;
            if (temp == null) {
                System.out.println("Doubly linked list is empty.");
                return;
            }
            while (temp != null) {
                System.out.println(temp.data);
                temp = temp.prev;
            }
        }
    }

    static class CircularLinkedList {
        static class Node {
            Student data;
            Node next;
            Node(Student data) { this.data = data; }
        }

        Node last;

        void insert(Student s) {
            Node n = new Node(s);
            if (last == null) {
                last = n;
                last.next = last;
                return;
            }
            n.next = last.next;
            last.next = n;
            last = n;
        }

        boolean delete(String roll) {
            if (last == null) return false;

            Node prev = last;
            Node cur = last.next;

            do {
                if (cur.data.rollNumber.equalsIgnoreCase(roll)) {
                    if (cur == last && cur == last.next) {
                        last = null;
                    } else {
                        prev.next = cur.next;
                        if (cur == last) last = prev;
                    }
                    return true;
                }
                prev = cur;
                cur = cur.next;
            } while (cur != last.next);

            return false;
        }

        void traverse() {
            if (last == null) {
                System.out.println("Circular linked list is empty.");
                return;
            }
            Node temp = last.next;
            do {
                System.out.println(temp.data);
                temp = temp.next;
            } while (temp != last.next);
        }

        boolean detectCycle() {
            return last != null && last.next != null;
        }
    }

    // ---------- CO3 circular queue ----------
    static class CircularQueue {
        Student[] arr;
        int front = -1, rear = -1, size;

        CircularQueue(int size) {
            this.size = size;
            arr = new Student[size];
        }

        boolean isEmpty() {
            return front == -1;
        }

        boolean isFull() {
            return (rear + 1) % size == front;
        }

        void enqueue(Student s) {
            if (isFull()) return;
            if (front == -1) front = 0;
            rear = (rear + 1) % size;
            arr[rear] = s;
        }

        Student dequeue() {
            if (isEmpty()) return null;
            Student s = arr[front];
            if (front == rear) {
                front = rear = -1;
            } else {
                front = (front + 1) % size;
            }
            return s;
        }

        void remove(String roll) {
            if (isEmpty()) return;

            Student[] temp = new Student[size];
            int count = 0;

            while (!isEmpty()) {
                Student s = dequeue();
                if (!s.rollNumber.equalsIgnoreCase(roll)) {
                    temp[count++] = s;
                }
            }

            for (int i = 0; i < count; i++) {
                enqueue(temp[i]);
            }
        }

        void display() {
            if (isEmpty()) {
                System.out.println("Circular queue is empty.");
                return;
            }
            int i = front;
            while (true) {
                System.out.println(arr[i]);
                if (i == rear) break;
                i = (i + 1) % size;
            }
        }
    }

    // ---------- CO4 hashing ----------
    static class ChainingHashTable {
        static class Entry {
            String key;
            Student value;
            Entry(String key, Student value) {
                this.key = key;
                this.value = value;
            }
        }

        ArrayList<LinkedList<Entry>> table;
        int size;

        ChainingHashTable(int size) {
            this.size = size;
            table = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                table.add(new LinkedList<>());
            }
        }

        int hash(String key) {
            return Math.abs(key.hashCode()) % size;
        }

        void put(String key, Student value) {
            int index = hash(key);
            for (Entry e : table.get(index)) {
                if (e.key.equalsIgnoreCase(key)) {
                    e.value = value;
                    return;
                }
            }
            table.get(index).add(new Entry(key, value));
        }

        Student get(String key) {
            int index = hash(key);
            for (Entry e : table.get(index)) {
                if (e.key.equalsIgnoreCase(key)) return e.value;
            }
            return null;
        }

        void remove(String key) {
            int index = hash(key);
            table.get(index).removeIf(e -> e.key.equalsIgnoreCase(key));
        }
    }

    static class OpenAddressingHashTable {
        static class Entry {
            String key;
            Student value;
            boolean deleted;

            Entry(String key, Student value) {
                this.key = key;
                this.value = value;
            }
        }

        Entry[] table;
        int capacity;
        int count;

        OpenAddressingHashTable(int capacity) {
            this.capacity = capacity;
            this.table = new Entry[capacity];
        }

        int hash(String key) {
            return Math.abs(key.hashCode()) % capacity;
        }

        double loadFactor() {
            return (double) count / capacity;
        }

        void rehash() {
            Entry[] old = table;
            capacity = capacity * 2;
            table = new Entry[capacity];
            count = 0;

            for (Entry e : old) {
                if (e != null && !e.deleted) {
                    put(e.key, e.value);
                }
            }
        }

        void put(String key, Student value) {
            if (loadFactor() > 0.7) rehash();

            int start = hash(key);
            for (int i = 0; i < capacity; i++) {
                int idx = (start + i) % capacity;
                if (table[idx] == null || table[idx].deleted || table[idx].key.equalsIgnoreCase(key)) {
                    if (table[idx] == null || table[idx].deleted) count++;
                    table[idx] = new Entry(key, value);
                    return;
                }
            }
        }

        Student get(String key) {
            int start = hash(key);
            for (int i = 0; i < capacity; i++) {
                int idx = (start + i) % capacity;
                if (table[idx] == null) return null;
                if (!table[idx].deleted && table[idx].key.equalsIgnoreCase(key)) {
                    return table[idx].value;
                }
            }
            return null;
        }

        void remove(String key) {
            int start = hash(key);
            for (int i = 0; i < capacity; i++) {
                int idx = (start + i) % capacity;
                if (table[idx] == null) return;
                if (!table[idx].deleted && table[idx].key.equalsIgnoreCase(key)) {
                    table[idx].deleted = true;
                    return;
                }
            }
        }
    }
}