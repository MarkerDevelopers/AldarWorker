스케줄러 순서:
WorkerMoveScheduler -> 'Worker' Working Scheduler (do working) -> WorkerMoveScheduler (Move to chest) -> WorkerMoveScheduler (Move to working area) -> 'Worker' Working Scheduler(do working)

낚시꾼:
- 주위 100 x 100의 물가를 찾습니다.
- 물가를 찾으면 그 장소로 이동합니다.
- 이동 후 낚시를 시작합니다.



error:
    현재 y좌표가 0.5정도 낮게 Move 되는 오류가 있음.

    Farmer Move location fix 해야함