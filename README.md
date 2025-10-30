# 💻 TP6 DPBO - Imam Azizun Hakim - 2404420


## 🤝 Janji
"Saya Imam Azizun Hakim dengan NIM 2404420 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain Pemrograman Berorientasi Objek untuk keberkahan-Nya maka saya tidak akan melakukan kecurangan seperti yang telah di spesifikasikan. Aamiin."


## 🔀 Penjelasan Desain dan Kode Flow

### Flappy Momoi! 
<p align="center">
  <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTE6eU2UV4hlxkX8bEftLQYwl0po5Phuw_XA&s" alt="Logo contoh" width="250" />
</p>

- Game yang basicnya adalah FlappyBird yang dimainkan dengan melewati setiap obstacle untuk menambah skor.
- Menggunakan 6 class bernama **_App_**, **_Pipe_**, **_Player_**, **_View_**, **_Logic_**, **_Sound_**

### Class App
Berisi main dari program dan menampilkan menu awal saat program dijalankan.

### Class Pipe
Berisi atribut dan method yang digunakan untuk mengatur Pipe di game.

### Class Player
Berisi atribut dan method yang digunakan untuk mengatur Player dalam game.

### Class View
Berisi atribut dan method yang digunakan untuk mengatur Tampilan game.

### Class Logic
Berisi atribut dan method yang digunakan untuk mengatur Logika game.

### Class Sound
Berisi atribut dan method yang digunakan untuk mengatur Suara dalam game.


### Flow Program
<pre>
  1. Program dimulai dengan data awal (hardcode)
  2. Menampilkan data awal Produk
  3. Menambahkan data baru dengan atribut yang harus lengkap (ada Error Handling)
  5. Mengedit data dengan atribut yang harus lengkap (ada Error Handling)
  6. Menghapus data dengan confirmation prompt
</pre>

### Connect Database
Pada Tugas Praktikum kali ini, menggunakan tugas sebelumnya yaitu TP4 dengan beberpaa perubahan di bagian data. Kali ini data diambil dari database MySQL, tidak dari hardcode seperti pada TP4. Untuk CRUD sudah tersambung ke Database MySQL. File data ***product.sql*** terdapat pada lampiran.
 
### Requirements
- Hubungkan semua proses CRUD dengan database ✅
- Hapus penggunaan variabel ArrayList. (*) ✅
- Tampilkan dialog/prompt error jika masih ada kolom input yang kosong saat insert/update ✅
- Tampilkan dialog/prompt error jika sudah ada ID yang sama saat insert ✅
    
## 📝 Dokumentasi
**Berikut adalah Dokumentasi berupa Screenshot saat program dijalankan di IntelliJ IDEA**

### Tampilan Awal
![01](Dokumentasi/01.png)

### ERROR HANDLING INSERT
![02](Dokumentasi/02.png)
![03](Dokumentasi/03.png)
![03](Dokumentasi/err.png)

### INSERT
![04](Dokumentasi/04.png)

### Database setelah Insert
![041](Dokumentasi/041.png)

### ERROR HANDLING UPDATE
![05](Dokumentasi/05.png)
![06](Dokumentasi/06.png)

### UPDATE
![08](Dokumentasi/08.png)

### Database setelah Update
![081](Dokumentasi/081.png)


### DELETE
![09](Dokumentasi/09.png)
![10](Dokumentasi/10.png)

### Database setelah Delete
![101](Dokumentasi/101.png)
