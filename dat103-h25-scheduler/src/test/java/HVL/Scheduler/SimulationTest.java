	package HVL.Scheduler;

	import org.junit.jupiter.api.Test;
	import org.junit.jupiter.api.BeforeEach;
	import static org.hamcrest.MatcherAssert.assertThat;
	import static org.hamcrest.Matchers.contains;

	import java.util.List;
	import java.util.Map;
	import java.util.stream.Collectors;
	import java.util.stream.Stream;

	public class SimulationTest {

	Map<Integer, List<Task>> arrivals;
	Simulation simulation;
	private Integer test=2;  // DO NOT change this for Subtasks 1 and 2(a)
							 // ONLY change the value from 1 to 2 for Subtask 2(b).

	@BeforeEach
	public void setUp() {
			simulation = new Simulation();
		switch (test) {
		//Test 1
		case 1:
				arrivals = Map.ofEntries(
					Map.entry(0, List.of(
							simulation.makeTask(5),
							simulation.makeTask(3),
							simulation.makeTask(1))),
			Map.entry(2, List.of(
							simulation.makeTask(1),
							simulation.makeTask(4))),
			Map.entry(6, List.of(
							simulation.makeTask(6),
							simulation.makeTask(4))),
					Map.entry(10, List.of(
							simulation.makeTask(2))),
			Map.entry(16, List.of(
							simulation.makeTask(1),
							simulation.makeTask(3))));
				break;
		//Test 2
		case 2:
				arrivals = Map.ofEntries(
					Map.entry(0, List.of(
							simulation.makeTask(1),
							simulation.makeTask(5),
							simulation.makeTask(3))),
			Map.entry(4, List.of(
							simulation.makeTask(2),
							simulation.makeTask(4),
							simulation.makeTask(6))),
			Map.entry(12, List.of(
							simulation.makeTask(5),
							simulation.makeTask(2))),
			Map.entry(16, List.of(
							simulation.makeTask(1),
							simulation.makeTask(4))));
			break;
		}
			simulation.setArrivals(arrivals);
		}

		@Test
		public void testRR() {
			var rrScheduler = new RRScheduler(simulation.getClock(),4);
			simulation.setScheduler(rrScheduler);

		switch (test) {
		case 1:  //Assert Test 1
			var steps = Stream.generate(() -> {
				simulation.step();

				var state = "T=%d %s".formatted(simulation.time(), rrScheduler.view());
				simulation.clocktick();
				return state;
			}).limit(31).collect(Collectors.toList());  //31 is the total execution time

			// Subtask 1: Write out expected view for 31 steps of Round Robin scheduling
		// Assert Test 1
			steps.forEach(System.out::println);
			assertThat(steps,contains(
					"T=0 Scheduled: T1 Ready: T2, T3",
					"T=1 Scheduled: T1 Ready: T2, T3",
					"T=2 Scheduled: T1 Ready: T2, T3, T4, T5",
					"T=3 Scheduled: T1 Ready: T2, T3, T4, T5",
					"T=4 Scheduled: T2 Ready: T3, T4, T5, T1",
					"T=5 Scheduled: T2 Ready: T3, T4, T5, T1",
					"T=6 Scheduled: T2 Ready: T3, T4, T5, T1, T6, T7",
					"T=7 Scheduled: T3 Ready: T4, T5, T1, T6, T7",
					"T=8 Scheduled: T4 Ready: T5, T1, T6, T7",
					"T=9 Scheduled: T5 Ready: T1, T6, T7",
					"T=10 Scheduled: T5 Ready: T1, T6, T7, T8",
					"T=11 Scheduled: T5 Ready: T1, T6, T7, T8",
					"T=12 Scheduled: T5 Ready: T1, T6, T7, T8",
					"T=13 Scheduled: T1 Ready: T6, T7, T8",
					"T=14 Scheduled: T6 Ready: T7, T8",
					"T=15 Scheduled: T6 Ready: T7, T8",
					"T=16 Scheduled: T6 Ready: T7, T8, T9, T10",
					"T=17 Scheduled: T6 Ready: T7, T8, T9, T10",
					"T=18 Scheduled: T7 Ready: T8, T9, T10, T6",
					"T=19 Scheduled: T7 Ready: T8, T9, T10, T6",
					"T=20 Scheduled: T7 Ready: T8, T9, T10, T6",
					"T=21 Scheduled: T7 Ready: T8, T9, T10, T6",
					"T=22 Scheduled: T8 Ready: T9, T10, T6",
					"T=23 Scheduled: T8 Ready: T9, T10, T6",
					"T=24 Scheduled: T9 Ready: T10, T6",
					"T=25 Scheduled: T10 Ready: T6",
					"T=26 Scheduled: T10 Ready: T6",
					"T=27 Scheduled: T10 Ready: T6",
					"T=28 Scheduled: T6 Ready: ",
					"T=29 Scheduled: T6 Ready: ",
					"T=30 Scheduled: Ready: "
			));
		break;

		case 2: 

		
		break;
		}
		}

		@Test
		public void testPSJF() {
			var psjfScheduler = new PSJFScheduler();
			simulation.setScheduler(psjfScheduler);

		switch (test) {
		//Assert Test 1
		case 1:
			var steps1 = Stream.generate(() -> {
				simulation.step();
				var state = "T=%d %s".formatted(simulation.time(), psjfScheduler.view());
				simulation.clocktick();
				return state;
			}).limit(31).collect(Collectors.toList());

			steps1.forEach(System.out::println);
			assertThat(steps1,contains(
					"T=0 Scheduled: T3 Ready: T1, T2",
					"T=1 Scheduled: T2 Ready: T1",
					"T=2 Scheduled: T4 Ready: T1, T2, T5",
					"T=3 Scheduled: T2 Ready: T1, T5",
					"T=4 Scheduled: T2 Ready: T1, T5",
					"T=5 Scheduled: T5 Ready: T1",
					"T=6 Scheduled: T5 Ready: T1, T6, T7",
					"T=7 Scheduled: T5 Ready: T1, T6, T7",
					"T=8 Scheduled: T5 Ready: T1, T6, T7",
					"T=9 Scheduled: T7 Ready: T1, T6",
					"T=10 Scheduled: T8 Ready: T1, T6, T7",
					"T=11 Scheduled: T8 Ready: T1, T6, T7",
					"T=12 Scheduled: T7 Ready: T1, T6",
					"T=13 Scheduled: T7 Ready: T1, T6",
					"T=14 Scheduled: T7 Ready: T1, T6",
					"T=15 Scheduled: T1 Ready: T6",
					"T=16 Scheduled: T9 Ready: T6, T1, T10",
					"T=17 Scheduled: T10 Ready: T6, T1",
					"T=18 Scheduled: T10 Ready: T6, T1",
					"T=19 Scheduled: T10 Ready: T6, T1",
					"T=20 Scheduled: T1 Ready: T6",
					"T=21 Scheduled: T1 Ready: T6",
					"T=22 Scheduled: T1 Ready: T6",
					"T=23 Scheduled: T1 Ready: T6",
					"T=24 Scheduled: T6 Ready: ",
					"T=25 Scheduled: T6 Ready: ",
					"T=26 Scheduled: T6 Ready: ",
					"T=27 Scheduled: T6 Ready: ",
					"T=28 Scheduled: T6 Ready: ",
					"T=29 Scheduled: T6 Ready: ",
					"T=30 Scheduled: Ready: "
			));
		break;

		//Assert Test 2
		case 2:
			var steps2 = Stream.generate(() -> {
				simulation.step();
				var state = "T=%d %s".formatted(simulation.time(), psjfScheduler.view());
				simulation.clocktick();
				return state;
			}).limit(34).collect(Collectors.toList());

		// Subtask 2(b): Write out expected view for 34 steps of PSJF scheduling
		// Assert Test 2
			steps2.forEach(System.out::println);
			assertThat(steps2,contains(
					"T=0 Scheduled: T1 Ready: T2, T3",
					"T=1 Scheduled: T3 Ready: T2",
					"T=2 Scheduled: T3 Ready: T2",
					"T=3 Scheduled: T3 Ready: T2",
					"T=4 Scheduled: T4 Ready: T2, T5, T6",
					"T=5 Scheduled: T4 Ready: T2, T5, T6",
					"T=6 Scheduled: T5 Ready: T2, T6",
					"T=7 Scheduled: T5 Ready: T2, T6",
					"T=8 Scheduled: T5 Ready: T2, T6",
					"T=9 Scheduled: T5 Ready: T2, T6",
					"T=10 Scheduled: T2 Ready: T6",
					"T=11 Scheduled: T2 Ready: T6",
					"T=12 Scheduled: T8 Ready: T6, T7, T2",
					"T=13 Scheduled: T8 Ready: T6, T7, T2",
					"T=14 Scheduled: T2 Ready: T6, T7",
					"T=15 Scheduled: T2 Ready: T6, T7",
					"T=16 Scheduled: T2 Ready: T6, T7, T9, T10",
					"T=17 Scheduled: T9 Ready: T6, T7, T10",
					"T=18 Scheduled: T10 Ready: T6, T7",
					"T=19 Scheduled: T10 Ready: T6, T7",
					"T=20 Scheduled: T10 Ready: T6, T7",
					"T=21 Scheduled: T10 Ready: T6, T7",
					"T=22 Scheduled: T7 Ready: T6",
					"T=23 Scheduled: T7 Ready: T6",
					"T=24 Scheduled: T7 Ready: T6",
					"T=25 Scheduled: T7 Ready: T6",
					"T=26 Scheduled: T7 Ready: T6",
					"T=27 Scheduled: T6 Ready: ",
					"T=28 Scheduled: T6 Ready: ",
					"T=29 Scheduled: T6 Ready: ",
					"T=30 Scheduled: T6 Ready: ",
					"T=31 Scheduled: T6 Ready: ",
					"T=32 Scheduled: T6 Ready: ",
					"T=33 Scheduled: Ready: "
			));
		break;
		}
		}
	}
