package com.furkan.tutorials.service;

import com.furkan.tutorials.model.Tutorial;
import com.furkan.tutorials.repository.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class TutorialServiceImplTest {

    @Mock
    private TutorialRepository tutorialRepository;

    @InjectMocks
    private TutorialServiceImpl tutorialService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_returnsList() {
        // Repository'den dönecek tüm tutorial'lar için simüle edilmiş bir liste oluşturuyoruz.
        List<Tutorial> tutorials = Arrays.asList(new Tutorial(), new Tutorial());
        // Repository findAll çağrıldığında oluşturduğumuz örnek listeyi döndürmesini sağlıyoruz.
        when(tutorialRepository.findAll()).thenReturn(tutorials);

        // Servis katmanındaki getAll metodunu çağırarak sonucu alıyoruz.
        List<Tutorial> result = tutorialService.getAll();

        // Dönen listenin boyutunun beklenen boyutta olduğunu doğruluyoruz.
        assertEquals(2, result.size());
        // findAll metodunun tam olarak bir kez çağrıldığını doğruluyoruz.
        verify(tutorialRepository, times(1)).findAll();
    }

    @Test
    public void testGetById_found() {
        // ID ile bulunan bir tutorial nesnesini simüle ediyoruz.
        Tutorial tutorial = new Tutorial();
        tutorial.setId("abc123");

        // Repository findById çağrıldığında ilgili tutorial nesnesini Optional olarak döndürmesini sağlıyoruz.
        when(tutorialRepository.findById("abc123")).thenReturn(Optional.of(tutorial));

        // Servis katmanındaki getById metodunu çağırarak sonucu alıyoruz.
        Optional<Tutorial> result = tutorialService.getById("abc123");

        // Sonucun var olduğunu ve doğru ID'ye sahip olduğunu doğruluyoruz.
        assertTrue(result.isPresent());
        assertEquals("abc123", result.get().getId());
    }

    @Test
    public void testCreate_success() {
        // Oluşturulacak yeni bir tutorial nesnesini hazırlıyoruz.
        Tutorial tutorial = new Tutorial();
        tutorial.setTitle("Test Title");

        // Repository save metodunun çağrıldığında aynı tutorial nesnesini dönmesini simüle ediyoruz.
        when(tutorialRepository.save(tutorial)).thenReturn(tutorial);

        // Servis katmanındaki create metodunu çağırıp sonucu alıyoruz.
        Tutorial result = tutorialService.create(tutorial);

        // Dönen nesnenin beklenen başlığa sahip olduğunu kontrol ediyoruz.
        assertEquals("Test Title", result.getTitle());
        // save metodunun bir kez çağrıldığını doğruluyoruz.
        verify(tutorialRepository, times(1)).save(tutorial);
    }

    @Test
    public void testUpdate_success() {
        // Var olan tutorial nesnesini simüle ediyoruz.
        Tutorial existing = new Tutorial();
        existing.setId("123");
        existing.setTitle("Old");
        existing.setDescription("Desc");
        existing.setPublished(false);

        // Güncelleme yapmak istediğimiz yeni tutorial bilgilerini oluşturuyoruz.
        Tutorial updated = new Tutorial();
        updated.setTitle("New");
        updated.setDescription("Updated desc");
        updated.setPublished(true);

        // Repository'den findById çağrıldığında var olan tutorial'ı döndürmesini sağlıyoruz.
        when(tutorialRepository.findById("123")).thenReturn(Optional.of(existing));
        // save metodu çağrıldığında verilen tutorial nesnesini döndürmesini sağlıyoruz.
        when(tutorialRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // Servis katmanındaki update metodunu çağırıp sonucu alıyoruz.
        Tutorial result = tutorialService.update("123", updated);

        // Güncellemenin başarılı olup olmadığını, tüm alanların doğru güncellendiğini doğruluyoruz.
        assertEquals("New", result.getTitle());
        assertEquals("Updated desc", result.getDescription());
        assertTrue(result.isPublished());
    }

    @Test
    public void testUpdate_notFound_returnsNull() {
        // Güncellenmek istenen tutorial bulunamadığında Optional.empty() döndürülmesini simüle ediyoruz.
        when(tutorialRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // Servis katmanındaki update metodunu çağırıyoruz ve sonuç olarak null bekliyoruz.
        Tutorial result = tutorialService.update("nonexistent", new Tutorial());

        // Sonucun null olduğunu doğruluyoruz.
        assertNull(result);
    }

    @Test
    public void testDelete_success() {
        // deleteById metodu çağrıldığında hiçbir şey yapılmamasını simüle ediyoruz (silme işlemi).
        doNothing().when(tutorialRepository).deleteById("123");

        // Servis katmanındaki delete metodunu çağırıyoruz.
        tutorialService.delete("123");

        // Repository'nin deleteById metodunun tam olarak bir kez çağrıldığını doğruluyoruz.
        verify(tutorialRepository, times(1)).deleteById("123");
    }


    @Test
    public void testGetByPublished_returnsPublishedList() {
        // Veritabanında "published" durumu true olan tutorial'lar için örnek liste oluşturuyoruz.
        List<Tutorial> publishedList = Arrays.asList(new Tutorial(), new Tutorial());

        // Repository'nin findByPublished(true) metodunun çağrılması durumunda örnek listeyi döndürmesini sağlıyoruz (mock).
        when(tutorialRepository.findByPublished(true)).thenReturn(publishedList);

        // Servis katmanındaki getByPublished metodunu çağırarak sonucu alıyoruz.
        List<Tutorial> result = tutorialService.getByPublished();

        // Dönen listenin boyutunun beklenen boyutta (2 elemanlı) olduğunu kontrol ediyoruz.
        assertEquals(2, result.size());

        // Repository metodunun tam olarak bir kere çağrıldığını doğruluyoruz.
        verify(tutorialRepository, times(1)).findByPublished(true);
    }

}
