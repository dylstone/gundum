# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table activity_code_master (
  activity_id               char(3) not null,
  activity_name             varchar2(30),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_activity_code_master primary key (activity_id))
;

create table apply_info (
  apply_id                  char(10) not null,
  uketsuke_id               char(10) not null,
  kokyaku_id                char(10),
  keiyaku_id                char(10),
  apply_status              char(4) not null,
  touroku_miss_flg          number(1) default 0,
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_apply_info primary key (apply_id))
;

create table apply_keiyaku_info (
  apply_keiyaku_id          char(10) not null,
  apply_id                  char(10),
  apply_kokyaku_id          char(10),
  keiyaku_id                char(10),
  henkou_type               char(4) not null,
  syounin_status            char(4),
  keiyaku_brand_cd          char(8),
  hanbai_keitai             char(2),
  moushikomi_nengetsubi     char(8),
  keiyaku_koushin_nengetsu  char(6),
  riyou_kaishi_nengetsubi   char(8),
  shiharai_houhou           varchar2(50),
  seikyuu_kubun             varchar2(50),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_apply_keiyaku_info primary key (apply_keiyaku_id))
;

create table apply_kokyaku_info (
  apply_kokyaku_id          char(10) not null,
  apply_id                  char(10),
  kokyaku_id                char(10),
  henkou_type               char(4) not null,
  syounin_status            char(4),
  kokyaku_user_id           varchar2(20),
  kokyaku_user_pwd          varchar2(20),
  last_name                 varchar2(16),
  first_name                varchar2(16),
  last_name_furigana        varchar2(32),
  first_name_furigana       varchar2(32),
  department                varchar2(32),
  corporative_user          varchar2(32),
  seibetsu_cd               char(1),
  birth_nengetsubi          char(8),
  job_cd                    char(4),
  mikomi_kokyaku_flg        char(1) default 0 not null,
  renrakusaki_mailaddress   varchar2(30),
  tel_type                  char(4),
  tel_no1                   varchar2(16),
  tel_no2                   varchar2(16),
  fax_no                    varchar2(16),
  zip_no                    varchar2(8),
  todouhuken                varchar2(8),
  si_ku_gun                 varchar2(100),
  tyouson_oaza              varchar2(100),
  aza_banchi_gou            varchar2(100),
  apartment                 varchar2(50),
  apartment_room_no         varchar2(30),
  kiduke_sama               varchar2(20),
  kokyaku_user_pwd_last_updated char(8),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_apply_kokyaku_info primary key (apply_kokyaku_id))
;

create table apply_service_info (
  apply_service_id          char(10) not null,
  apply_id                  char(10),
  apply_keiyaku_id          char(10),
  service_id                char(10) not null,
  henkou_type               char(4) not null,
  syounin_status            char(4),
  service_cd                char(4),
  riyou_kaishi_nengetsubi   char(8),
  riyou_course              varchar2(100),
  riyou_plan                varchar2(100),
  ipv6adapter               varchar2(50),
  mailaddress1              varchar2(30),
  mailaddress2              varchar2(30),
  tt_phone_status           varchar2(50),
  sokuwari15                varchar2(50),
  kaitsuu_koujibi           char(8),
  kouji_yoteibi             char(8),
  depend_service_cd1        char(4),
  depend_service_cd2        char(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_apply_service_info primary key (apply_service_id))
;

create table apply_svc_kaisen (
  apply_svc_auhikari_id     char(10) not null,
  service_id                char(10),
  service_cd                char(4),
  riyou_course              varchar2(100),
  riyou_plan                varchar2(100),
  ipv6adapter               varchar2(50),
  tt_phone_status           varchar2(50),
  sokuwari15                varchar2(50),
  kaitsuu_koujibi           char(8),
  kouji_yoteibi             char(8),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_apply_svc_kaisen primary key (apply_svc_auhikari_id))
;

create table apply_svc_mail (
  apply_svc_mail_id         char(10) not null,
  service_id                char(10),
  service_cd                char(4),
  mailaddress               varchar2(30),
  depend_service_cd1        char(4),
  depend_service_cd2        char(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_apply_svc_mail primary key (apply_svc_mail_id))
;

create table apply_svc_option (
  apply_svc_option_id       char(10) not null,
  service_id                char(10),
  service_cd                char(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_apply_svc_option primary key (apply_svc_option_id))
;

create table apply_svc_security (
  apply_svc_security_id     char(10) not null,
  service_id                char(10),
  service_cd                char(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_apply_svc_security primary key (apply_svc_security_id))
;

create table apply_uketsuke_key (
  queue_id                  char(10) not null,
  kokyaku_id                char(10),
  keiyaku_id                char(10),
  service_id                char(10),
  apply_id                  char(10),
  activity_id               char(3) not null,
  brand_cd                  char(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_apply_uketsuke_key primary key (queue_id))
;

create table apply_xml (
  apply_id                  char(10) not null,
  apply_xml                 CLob,
  uketsuke_id               char(10),
  business_detail           char(1) not null,
  kokyaku_id                char(10) not null,
  keiyaku_id                char(10) not null,
  uketsuke_date             char(8),
  apply_date                char(8),
  apply_ver_cd              char(4),
  uketsuke_route_cd         char(2),
  brand_cd                  char(4) not null,
  error_cd                  char(1) not null,
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_apply_xml primary key (apply_id))
;

create table gamen_item_control_info (
  sub_frame_id              char(8) not null,
  gamen_part_id             char(8) not null,
  gamen_item_id             char(8) not null,
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_gamen_item_control_info primary key (sub_frame_id, gamen_part_id, gamen_item_id))
;

create table getsuji_ryoukin_info (
  kakin_id                  char(10) not null,
  seikyuu_id                char(10) not null,
  keiyaku_id                char(10),
  taisyou_month             number(2) not null,
  sougaku                   number(20) default 0,
  tax                       number(10) default 0,
  getsugaku_ryoukin         number(10) default 0,
  hiwari_ryoukin            number(10) default 0,
  ichijikin                 number(10) default 0,
  waribikigaku              number(10) default 0,
  zyuuryou_ryoukin          number(10) default 0,
  tyousei_kingaku           number(10) default 0,
  azukarihin_kingaku        number(10) default 0,
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_getsuji_ryoukin_info primary key (kakin_id))
;

create table id_info (
  id_cd                     char(4) not null,
  id_type_string            varchar2(5) not null,
  id_no_digit               number(2) default 0 not null,
  id_no                     number(10) default 0 not null,
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_id_info primary key (id_cd))
;

create table keiyaku_info (
  keiyaku_id                char(10) not null,
  kokyaku_id                char(10),
  keiyaku_brand_cd          char(8),
  status                    char(4) not null,
  hanbai_keitai             char(2),
  moushikomi_nengetsubi     char(8),
  keiyaku_koushin_nengetsu  char(6),
  riyou_kaishi_nengetsubi   char(8),
  shiharai_houhou           varchar2(50),
  seikyuu_kubun             varchar2(50),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_keiyaku_info primary key (keiyaku_id))
;

create table kokyaku_info (
  kokyaku_id                char(10) not null,
  kokyaku_user_id           varchar2(20),
  kokyaku_user_pwd          varchar2(20),
  status                    char(4) not null,
  last_name                 varchar2(16),
  first_name                varchar2(16),
  last_name_furigana        varchar2(32),
  first_name_furigana       varchar2(32),
  department                varchar2(32),
  corporative_user          varchar2(32),
  seibetsu_cd               char(1),
  birth_nengetsubi          char(8),
  job_cd                    char(4),
  mikomi_kokyaku_flg        char(1) default 0 not null,
  renrakusaki_mailaddress   varchar2(30),
  tel_type                  char(4),
  tel_no1                   varchar2(16),
  tel_no2                   varchar2(16),
  fax_no                    varchar2(16),
  zip_no                    varchar2(8),
  todouhuken                varchar2(8),
  si_ku_gun                 varchar2(100),
  tyouson_oaza              varchar2(100),
  aza_banchi_gou            varchar2(100),
  apartment                 varchar2(50),
  apartment_room_no         varchar2(30),
  kiduke_sama               varchar2(20),
  kokyaku_user_pwd_last_updated char(8),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_kokyaku_info primary key (kokyaku_id))
;

create table my_page_setting (
  id                        number(10) not null,
  user_id                   varchar2(255),
  kinou_id                  varchar2(255),
  setting_id                varchar2(255),
  subframe_id               varchar2(255),
  x                         number(10),
  y                         number(10),
  width                     number(10),
  height                    number(10),
  z_index                   number(10),
  visiblity                 varchar2(255),
  update_at                 timestamp not null,
  constraint uq_my_page_setting_1 unique (user_id,kinou_id,setting_id,subframe_id),
  constraint pk_my_page_setting primary key (id))
;

create table mypage_control_info (
  operator_id               varchar2(10),
  function                  char(8),
  pattern                   char(4),
  sub_frame_id              char(8),
  left_position             number(11,3) default 0,
  top_position              number(11,3) default 0,
  width                     number(11,3) default 0,
  height                    number(11,3) default 0,
  stack_order               number(2) default 0,
  display                   number(1) default 0,
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_mypage_control_info primary key (operator_id, function, pattern, sub_frame_id))
;

create table new_apply_service_info (
  apply_service_id          char(10) not null,
  apply_id                  char(10),
  apply_keiyaku_id          char(10),
  service_id                char(10) not null,
  henkou_type               char(4) not null,
  syounin_status            char(4),
  riyou_kaishi_nengetsubi   char(8),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_new_apply_service_info primary key (apply_service_id))
;

create table new_service_info (
  service_id                char(10) not null,
  keiyaku_id                char(10),
  status                    char(4) not null,
  riyou_kaishi_nengetsubi   char(8),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_new_service_info primary key (service_id))
;

create table new_service_master (
  service_cd                char(4) not null,
  service_meishou           varchar2(100),
  service_bunrui_cd         char(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_new_service_master primary key (service_cd))
;

create table new_service_ryoukin_master (
  service_ryoukin_cd        char(4) not null,
  service_cd                char(4),
  getsugaku_ryoukin         number(10) default 0,
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_new_service_ryoukin_master primary key (service_ryoukin_cd))
;

create table operator_master (
  operator_id               varchar2(10) not null,
  operator_name             varchar2(32),
  operator_pwd              varchar2(20),
  operator_department       varchar2(30),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_operator_master primary key (operator_id))
;

create table procedure_service_master (
  shohin_cd                 char(4) not null,
  business_detail           char(1) not null,
  service_cd                char(4) not null,
  tekiyo_start_date         char(8),
  procedure_type            varchar2(3) not null,
  tekiyo_end_date           char(8),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_procedure_service_master primary key (shohin_cd, business_detail, service_cd, tekiyo_start_date))
;

create table procedure_type_master (
  service_cd                char(4) not null,
  business_detail           char(1) not null,
  apply_detail              char(1) not null,
  procedure_type            varchar2(3) not null,
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_procedure_type_master primary key (service_cd, business_detail, apply_detail))
;

create table progress_queue (
  queue_id                  char(10) not null,
  kokyaku_id                char(10),
  keiyaku_id                char(10),
  service_id                char(10),
  transaction_id            char(10),
  main_tantou_kinou         char(2) not null,
  current_tantou_kinou      char(2) not null,
  activity_id               char(3) not null,
  if_key                    char(10),
  other_key_column          varchar2(32),
  other_key_data            varchar2(100),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_progress_queue primary key (queue_id))
;

create table soinput (
  so_id                     char(10) not null,
  kokyaku_id                char(10),
  keiyaku_id                char(10),
  service_id                char(10),
  transaction_id            char(10),
  status                    char(2) not null,
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_soinput primary key (so_id))
;

create table sooutput (
  so_id                     char(10) not null,
  kokyaku_id                char(10),
  keiyaku_id                char(10),
  service_id                char(10),
  transaction_id            char(10),
  status                    char(2) not null,
  so_result_column          varchar2(32),
  so_result_data            varchar2(100),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_sooutput primary key (so_id))
;

create table service_bunrui_master (
  service_bunrui_cd         char(4) not null,
  service_meishou           varchar2(100),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_service_bunrui_master primary key (service_bunrui_cd))
;

create table service_info (
  service_id                char(10) not null,
  keiyaku_id                char(10),
  status                    char(4) not null,
  service_cd                char(4),
  riyou_kaishi_nengetsubi   char(8),
  riyou_course              varchar2(100),
  riyou_plan                varchar2(100),
  ipv6adapter               varchar2(50),
  mailaddress1              varchar2(30),
  mailaddress2              varchar2(30),
  tt_phone_status           varchar2(50),
  sokuwari15                varchar2(50),
  kaitsuu_koujibi           char(8),
  kouji_yoteibi             char(8),
  depend_service_cd1        char(4),
  depend_service_cd2        char(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_service_info primary key (service_id))
;

create table service_master (
  service_cd                char(4) not null,
  service_meishou           varchar2(100),
  service_bunrui            char(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_service_master primary key (service_cd))
;

create table service_ryoukin_master (
  service_ryoukin_cd        char(4) not null,
  service_cd                char(4),
  getsugaku_ryoukin         number(10) default 0,
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_service_ryoukin_master primary key (service_ryoukin_cd))
;

create table svc_kaisen (
  svc_kaisen_id             char(10) not null,
  service_id                char(10),
  service_cd                char(4),
  riyou_course              varchar2(100),
  riyou_plan                varchar2(100),
  ipv6adapter               varchar2(50),
  tt_phone_status           varchar2(50),
  sokuwari15                varchar2(50),
  kaitsuu_koujibi           char(8),
  kouji_yoteibi             char(8),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_svc_kaisen primary key (svc_kaisen_id))
;

create table svc_mail (
  svc_mail_id               char(10) not null,
  service_id                char(10),
  service_cd                char(4),
  mailaddress               varchar2(30),
  depend_service_cd1        char(4),
  depend_service_cd2        char(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_svc_mail primary key (svc_mail_id))
;

create table svc_option (
  svc_option_id             char(10) not null,
  service_id                char(10),
  service_cd                char(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_svc_option primary key (svc_option_id))
;

create table svc_security (
  svc_security_id           char(10) not null,
  service_id                char(10),
  service_cd                char(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_svc_security primary key (svc_security_id))
;

create table swf_teigi_master (
  service_cd                char(4) not null,
  business_detail           char(1) not null,
  apply_detail              char(1) not null,
  procedure_type            varchar2(3) not null,
  tekiyo_start_date         char(8),
  tekiyo_end_date           char(8),
  script_file_info          varchar2(100),
  start_status              varchar2(4),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_swf_teigi_master primary key (service_cd, business_detail, apply_detail, procedure_type, tekiyo_start_date))
;

create table taiou_rireki (
  taiou_id                  char(10) not null,
  kokyaku_id                char(10) not null,
  renrakusha_name           varchar2(32),
  tel_no                    varchar2(16),
  taiou_nichiji             timestamp,
  taiou_operator_id         varchar2(10),
  kanren_client_taiou_id    char(10),
  toiawase_naiyou           varchar2(400),
  toiawase_syubetsu         char(4),
  taiou_naiyou              varchar2(400),
  taiou_kekka               varchar2(400),
  tsuuchi_naiyou            varchar2(400),
  rireki_touroku_nengetsubi char(8),
  rireki_touroku_operator_id varchar2(10),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_taiou_rireki primary key (taiou_id))
;

create table trans_service_info (
  transaction_id            char(10) not null,
  kokyaku_id                char(10) not null,
  keiyaku_id                char(10) not null,
  service_id                char(10) not null,
  status                    char(4) not null,
  service_cd                char(4),
  riyou_kaishi_nengetsubi   char(8),
  riyou_course              varchar2(100),
  riyou_plan                varchar2(100),
  ipv6adapter               varchar2(50),
  mailaddress               varchar2(30),
  tt_phone_status           varchar2(50),
  sokuwari15                varchar2(50),
  kaitsuu_koujibi           char(8),
  kouji_yoteibi             char(8),
  depend_service_cd1        char(4),
  depend_service_cd2        char(4),
  business_detail           char(1) not null,
  apply_detail              char(1) not null,
  procedure_type            varchar2(3) not null,
  touroku_flg               char(1) not null,
  so_status                 varchar2(4),
  interval_time             number(4) default 0 not null,
  last_run_date             timestamp default sysdate not null,
  swf_file_info             varchar2(100),
  delete_date               char(8),
  create_user               varchar2(32) not null,
  last_update_user          varchar2(32) not null,
  create_dt                 timestamp default sysdate not null,
  last_update_dt            timestamp default sysdate not null,
  constraint pk_trans_service_info primary key (transaction_id))
;

create sequence activity_code_master_seq;

create sequence apply_info_seq;

create sequence apply_keiyaku_info_seq;

create sequence apply_kokyaku_info_seq;

create sequence apply_service_info_seq;

create sequence apply_svc_kaisen_seq;

create sequence apply_svc_mail_seq;

create sequence apply_svc_option_seq;

create sequence apply_svc_security_seq;

create sequence apply_uketsuke_key_seq;

create sequence apply_xml_seq;

create sequence gamen_item_control_info_seq;

create sequence getsuji_ryoukin_info_seq;

create sequence id_info_seq;

create sequence keiyaku_info_seq;

create sequence kokyaku_info_seq;

create sequence my_page_setting_seq;

create sequence mypage_control_info_seq;

create sequence new_apply_service_info_seq;

create sequence new_service_info_seq;

create sequence new_service_master_seq;

create sequence new_service_ryoukin_master_seq;

create sequence operator_master_seq;

create sequence procedure_service_master_seq;

create sequence procedure_type_master_seq;

create sequence progress_queue_seq;

create sequence soinput_seq;

create sequence sooutput_seq;

create sequence service_bunrui_master_seq;

create sequence service_info_seq;

create sequence service_master_seq;

create sequence service_ryoukin_master_seq;

create sequence svc_kaisen_seq;

create sequence svc_mail_seq;

create sequence svc_option_seq;

create sequence svc_security_seq;

create sequence swf_teigi_master_seq;

create sequence taiou_rireki_seq;

create sequence trans_service_info_seq;

alter table apply_info add constraint fk_apply_info_kokyakuInfo_1 foreign key (kokyaku_id) references kokyaku_info (kokyaku_id);
create index ix_apply_info_kokyakuInfo_1 on apply_info (kokyaku_id);
alter table apply_info add constraint fk_apply_info_keiyakuInfo_2 foreign key (keiyaku_id) references keiyaku_info (keiyaku_id);
create index ix_apply_info_keiyakuInfo_2 on apply_info (keiyaku_id);
alter table apply_keiyaku_info add constraint fk_apply_keiyaku_info_applyI_3 foreign key (apply_id) references apply_info (apply_id);
create index ix_apply_keiyaku_info_applyI_3 on apply_keiyaku_info (apply_id);
alter table apply_keiyaku_info add constraint fk_apply_keiyaku_info_applyK_4 foreign key (apply_kokyaku_id) references apply_kokyaku_info (apply_kokyaku_id);
create index ix_apply_keiyaku_info_applyK_4 on apply_keiyaku_info (apply_kokyaku_id);
alter table apply_keiyaku_info add constraint fk_apply_keiyaku_info_keiyak_5 foreign key (keiyaku_id) references keiyaku_info (keiyaku_id);
create index ix_apply_keiyaku_info_keiyak_5 on apply_keiyaku_info (keiyaku_id);
alter table apply_kokyaku_info add constraint fk_apply_kokyaku_info_applyI_6 foreign key (apply_id) references apply_info (apply_id);
create index ix_apply_kokyaku_info_applyI_6 on apply_kokyaku_info (apply_id);
alter table apply_kokyaku_info add constraint fk_apply_kokyaku_info_kokyak_7 foreign key (kokyaku_id) references kokyaku_info (kokyaku_id);
create index ix_apply_kokyaku_info_kokyak_7 on apply_kokyaku_info (kokyaku_id);
alter table apply_service_info add constraint fk_apply_service_info_applyI_8 foreign key (apply_id) references apply_info (apply_id);
create index ix_apply_service_info_applyI_8 on apply_service_info (apply_id);
alter table apply_service_info add constraint fk_apply_service_info_applyK_9 foreign key (apply_keiyaku_id) references apply_keiyaku_info (apply_keiyaku_id);
create index ix_apply_service_info_applyK_9 on apply_service_info (apply_keiyaku_id);
alter table apply_service_info add constraint fk_apply_service_info_servi_10 foreign key (service_cd) references service_master (service_cd);
create index ix_apply_service_info_servi_10 on apply_service_info (service_cd);
alter table apply_service_info add constraint fk_apply_service_info_depen_11 foreign key (depend_service_cd1) references service_master (service_cd);
create index ix_apply_service_info_depen_11 on apply_service_info (depend_service_cd1);
alter table apply_service_info add constraint fk_apply_service_info_depen_12 foreign key (depend_service_cd2) references service_master (service_cd);
create index ix_apply_service_info_depen_12 on apply_service_info (depend_service_cd2);
alter table apply_svc_kaisen add constraint fk_apply_svc_kaisen_applySe_13 foreign key (service_id) references new_apply_service_info (apply_service_id);
create index ix_apply_svc_kaisen_applySe_13 on apply_svc_kaisen (service_id);
alter table apply_svc_kaisen add constraint fk_apply_svc_kaisen_service_14 foreign key (service_cd) references new_service_master (service_cd);
create index ix_apply_svc_kaisen_service_14 on apply_svc_kaisen (service_cd);
alter table apply_svc_mail add constraint fk_apply_svc_mail_applyServ_15 foreign key (service_id) references new_apply_service_info (apply_service_id);
create index ix_apply_svc_mail_applyServ_15 on apply_svc_mail (service_id);
alter table apply_svc_mail add constraint fk_apply_svc_mail_serviceMa_16 foreign key (service_cd) references new_service_master (service_cd);
create index ix_apply_svc_mail_serviceMa_16 on apply_svc_mail (service_cd);
alter table apply_svc_mail add constraint fk_apply_svc_mail_dependSer_17 foreign key (depend_service_cd1) references new_service_master (service_cd);
create index ix_apply_svc_mail_dependSer_17 on apply_svc_mail (depend_service_cd1);
alter table apply_svc_mail add constraint fk_apply_svc_mail_dependSer_18 foreign key (depend_service_cd2) references new_service_master (service_cd);
create index ix_apply_svc_mail_dependSer_18 on apply_svc_mail (depend_service_cd2);
alter table apply_svc_option add constraint fk_apply_svc_option_applySe_19 foreign key (service_id) references new_apply_service_info (apply_service_id);
create index ix_apply_svc_option_applySe_19 on apply_svc_option (service_id);
alter table apply_svc_option add constraint fk_apply_svc_option_service_20 foreign key (service_cd) references new_service_master (service_cd);
create index ix_apply_svc_option_service_20 on apply_svc_option (service_cd);
alter table apply_svc_security add constraint fk_apply_svc_security_apply_21 foreign key (service_id) references new_apply_service_info (apply_service_id);
create index ix_apply_svc_security_apply_21 on apply_svc_security (service_id);
alter table apply_svc_security add constraint fk_apply_svc_security_servi_22 foreign key (service_cd) references new_service_master (service_cd);
create index ix_apply_svc_security_servi_22 on apply_svc_security (service_cd);
alter table getsuji_ryoukin_info add constraint fk_getsuji_ryoukin_info_kei_23 foreign key (keiyaku_id) references keiyaku_info (keiyaku_id);
create index ix_getsuji_ryoukin_info_kei_23 on getsuji_ryoukin_info (keiyaku_id);
alter table keiyaku_info add constraint fk_keiyaku_info_kokyakuInfo_24 foreign key (kokyaku_id) references kokyaku_info (kokyaku_id);
create index ix_keiyaku_info_kokyakuInfo_24 on keiyaku_info (kokyaku_id);
alter table new_apply_service_info add constraint fk_new_apply_service_info_a_25 foreign key (apply_id) references apply_info (apply_id);
create index ix_new_apply_service_info_a_25 on new_apply_service_info (apply_id);
alter table new_apply_service_info add constraint fk_new_apply_service_info_a_26 foreign key (apply_keiyaku_id) references apply_keiyaku_info (apply_keiyaku_id);
create index ix_new_apply_service_info_a_26 on new_apply_service_info (apply_keiyaku_id);
alter table new_service_info add constraint fk_new_service_info_keiyaku_27 foreign key (keiyaku_id) references keiyaku_info (keiyaku_id);
create index ix_new_service_info_keiyaku_27 on new_service_info (keiyaku_id);
alter table new_service_master add constraint fk_new_service_master_servi_28 foreign key (service_bunrui_cd) references service_bunrui_master (service_bunrui_cd);
create index ix_new_service_master_servi_28 on new_service_master (service_bunrui_cd);
alter table new_service_ryoukin_master add constraint fk_new_service_ryoukin_mast_29 foreign key (service_cd) references new_service_master (service_cd);
create index ix_new_service_ryoukin_mast_29 on new_service_ryoukin_master (service_cd);
alter table service_info add constraint fk_service_info_keiyakuInfo_30 foreign key (keiyaku_id) references keiyaku_info (keiyaku_id);
create index ix_service_info_keiyakuInfo_30 on service_info (keiyaku_id);
alter table service_info add constraint fk_service_info_serviceMast_31 foreign key (service_cd) references service_master (service_cd);
create index ix_service_info_serviceMast_31 on service_info (service_cd);
alter table service_info add constraint fk_service_info_dependServi_32 foreign key (depend_service_cd1) references service_master (service_cd);
create index ix_service_info_dependServi_32 on service_info (depend_service_cd1);
alter table service_info add constraint fk_service_info_dependServi_33 foreign key (depend_service_cd2) references service_master (service_cd);
create index ix_service_info_dependServi_33 on service_info (depend_service_cd2);
alter table service_ryoukin_master add constraint fk_service_ryoukin_master_s_34 foreign key (service_cd) references service_master (service_cd);
create index ix_service_ryoukin_master_s_34 on service_ryoukin_master (service_cd);
alter table svc_kaisen add constraint fk_svc_kaisen_service_35 foreign key (service_id) references new_service_info (service_id);
create index ix_svc_kaisen_service_35 on svc_kaisen (service_id);
alter table svc_kaisen add constraint fk_svc_kaisen_serviceMaster_36 foreign key (service_cd) references new_service_master (service_cd);
create index ix_svc_kaisen_serviceMaster_36 on svc_kaisen (service_cd);
alter table svc_mail add constraint fk_svc_mail_service_37 foreign key (service_id) references new_service_info (service_id);
create index ix_svc_mail_service_37 on svc_mail (service_id);
alter table svc_mail add constraint fk_svc_mail_serviceMaster_38 foreign key (service_cd) references new_service_master (service_cd);
create index ix_svc_mail_serviceMaster_38 on svc_mail (service_cd);
alter table svc_mail add constraint fk_svc_mail_dependService1_39 foreign key (depend_service_cd1) references new_service_master (service_cd);
create index ix_svc_mail_dependService1_39 on svc_mail (depend_service_cd1);
alter table svc_mail add constraint fk_svc_mail_dependService2_40 foreign key (depend_service_cd2) references new_service_master (service_cd);
create index ix_svc_mail_dependService2_40 on svc_mail (depend_service_cd2);
alter table svc_option add constraint fk_svc_option_service_41 foreign key (service_id) references new_service_info (service_id);
create index ix_svc_option_service_41 on svc_option (service_id);
alter table svc_option add constraint fk_svc_option_serviceMaster_42 foreign key (service_cd) references new_service_master (service_cd);
create index ix_svc_option_serviceMaster_42 on svc_option (service_cd);
alter table svc_security add constraint fk_svc_security_service_43 foreign key (service_id) references new_service_info (service_id);
create index ix_svc_security_service_43 on svc_security (service_id);
alter table svc_security add constraint fk_svc_security_serviceMast_44 foreign key (service_cd) references new_service_master (service_cd);
create index ix_svc_security_serviceMast_44 on svc_security (service_cd);
alter table trans_service_info add constraint fk_trans_service_info_servi_45 foreign key (service_cd) references new_service_master (service_cd);
create index ix_trans_service_info_servi_45 on trans_service_info (service_cd);
alter table trans_service_info add constraint fk_trans_service_info_depen_46 foreign key (depend_service_cd1) references new_service_master (service_cd);
create index ix_trans_service_info_depen_46 on trans_service_info (depend_service_cd1);
alter table trans_service_info add constraint fk_trans_service_info_depen_47 foreign key (depend_service_cd2) references new_service_master (service_cd);
create index ix_trans_service_info_depen_47 on trans_service_info (depend_service_cd2);



# --- !Downs

drop table activity_code_master cascade constraints purge;

drop table apply_info cascade constraints purge;

drop table apply_keiyaku_info cascade constraints purge;

drop table apply_kokyaku_info cascade constraints purge;

drop table apply_service_info cascade constraints purge;

drop table apply_svc_kaisen cascade constraints purge;

drop table apply_svc_mail cascade constraints purge;

drop table apply_svc_option cascade constraints purge;

drop table apply_svc_security cascade constraints purge;

drop table apply_uketsuke_key cascade constraints purge;

drop table apply_xml cascade constraints purge;

drop table gamen_item_control_info cascade constraints purge;

drop table getsuji_ryoukin_info cascade constraints purge;

drop table id_info cascade constraints purge;

drop table keiyaku_info cascade constraints purge;

drop table kokyaku_info cascade constraints purge;

drop table my_page_setting cascade constraints purge;

drop table mypage_control_info cascade constraints purge;

drop table new_apply_service_info cascade constraints purge;

drop table new_service_info cascade constraints purge;

drop table new_service_master cascade constraints purge;

drop table new_service_ryoukin_master cascade constraints purge;

drop table operator_master cascade constraints purge;

drop table procedure_service_master cascade constraints purge;

drop table procedure_type_master cascade constraints purge;

drop table progress_queue cascade constraints purge;

drop table soinput cascade constraints purge;

drop table sooutput cascade constraints purge;

drop table service_bunrui_master cascade constraints purge;

drop table service_info cascade constraints purge;

drop table service_master cascade constraints purge;

drop table service_ryoukin_master cascade constraints purge;

drop table svc_kaisen cascade constraints purge;

drop table svc_mail cascade constraints purge;

drop table svc_option cascade constraints purge;

drop table svc_security cascade constraints purge;

drop table swf_teigi_master cascade constraints purge;

drop table taiou_rireki cascade constraints purge;

drop table trans_service_info cascade constraints purge;

drop sequence activity_code_master_seq;

drop sequence apply_info_seq;

drop sequence apply_keiyaku_info_seq;

drop sequence apply_kokyaku_info_seq;

drop sequence apply_service_info_seq;

drop sequence apply_svc_kaisen_seq;

drop sequence apply_svc_mail_seq;

drop sequence apply_svc_option_seq;

drop sequence apply_svc_security_seq;

drop sequence apply_uketsuke_key_seq;

drop sequence apply_xml_seq;

drop sequence gamen_item_control_info_seq;

drop sequence getsuji_ryoukin_info_seq;

drop sequence id_info_seq;

drop sequence keiyaku_info_seq;

drop sequence kokyaku_info_seq;

drop sequence my_page_setting_seq;

drop sequence mypage_control_info_seq;

drop sequence new_apply_service_info_seq;

drop sequence new_service_info_seq;

drop sequence new_service_master_seq;

drop sequence new_service_ryoukin_master_seq;

drop sequence operator_master_seq;

drop sequence procedure_service_master_seq;

drop sequence procedure_type_master_seq;

drop sequence progress_queue_seq;

drop sequence soinput_seq;

drop sequence sooutput_seq;

drop sequence service_bunrui_master_seq;

drop sequence service_info_seq;

drop sequence service_master_seq;

drop sequence service_ryoukin_master_seq;

drop sequence svc_kaisen_seq;

drop sequence svc_mail_seq;

drop sequence svc_option_seq;

drop sequence svc_security_seq;

drop sequence swf_teigi_master_seq;

drop sequence taiou_rireki_seq;

drop sequence trans_service_info_seq;

