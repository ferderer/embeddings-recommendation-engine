<script>
  import axios from 'redaxios';
  import PaginatedTable from './PaginatedTable.svelte';

  let available = $state([]);
  let booked = $state([]);
  let recommended = $state([]);

  $effect(() => {
    axios('api/lessons')
      .then(response => available = response.data);
  });

  $effect(() => {
    recommended = [];
    if (booked.length) {
      axios(`/api/recommend?ids=${booked.map(lesson => lesson.id).join(',')}`)
        .then(response => recommended = response.data);
    }
  });

  function bookLesson(lesson) {
    lesson.booked = new Date();
    booked = [lesson, ...booked]
      .sort((a, b) => b.booked - a.booked);
  }

  function formatTime(timestamp) {
    return timestamp.toLocaleTimeString('en-US', { hour12: false, hour: '2-digit',
      minute: '2-digit', second: '2-digit' });
  }
</script>

<main>
  <div class="card">
    <h2>Available Lessons</h2>
    <PaginatedTable data={available} headers={['Title', 'CEFR', 'Language', '']}>
      {#snippet row(lesson)}
        <td>{lesson.title}</td>
        <td>{lesson.cefr}</td>
        <td>{lesson.lang}</td>
        <td>
          {#if !lesson.booked}
            <button onclick={() => bookLesson(lesson)}>Book</button>
          {:else}
            <span class="success">✓ Booked</span>
          {/if}
        </td>
      {/snippet}
    </PaginatedTable>
  </div>

  {#if booked.length}
    <div class="card">
      <h2>Booked Lessons</h2>
      <PaginatedTable data={booked} headers={['Title', 'CEFR', 'Language', 'Booked at']}>
        {#snippet row(lesson)}
          <td>{lesson.title}</td>
          <td>{lesson.cefr}</td>
          <td>{lesson.lang}</td>
          <td>{formatTime(lesson.booked)}</td>
        {/snippet}
      </PaginatedTable>
    </div>
  {/if}

  {#if recommended.length}
    <div class="card">
      <h2>Recommended Lessons</h2>
      <PaginatedTable data={recommended} headers={['Title', 'CEFR', 'Language', '']}>
        {#snippet row(lesson)}
          <td>{lesson.title}</td>
          <td>{lesson.cefr}</td>
          <td>{lesson.lang}</td>
          <td>
            {#if !lesson.booked}
              <button onclick={() => bookLesson(lesson)}>Book</button>
            {:else}
              <span class="success">✓ Booked</span>
            {/if}
          </td>
        {/snippet}
      </PaginatedTable>
    </div>
  {/if}
</main>
