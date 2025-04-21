<script>
  let { data = [], headers = [], size = 10, row } = $props();
  let page = $state(0);
  const start = $derived(Math.min(page*size, data.length));
  const end = $derived(Math.min((page + 1)*size, data.length));
  const pages = $derived(Math.ceil(data.length / size));

  function* range(from, to) {
    while(from < to) yield from++
  }
</script>

<table>
  <thead>
    <tr>
      {#each headers as header}
        <th>{header}</th>
      {/each}
    </tr>
  </thead>
  <tbody>
    {#each [...range(start, end)] as i}
      <tr>{@render row(data[i])}</tr>
    {/each}
  </tbody>
  {#if pages > 1}
    <tfoot>
      <tr>
        <td colspan="4">
          <div>
            {#each [...range(0, pages)] as i}
              <button onclick={() => page = i} class:active={i === page}>{i + 1}</button>
            {/each}
          </div>
        </td>
      </tr>
    </tfoot>
  {/if}
</table>
