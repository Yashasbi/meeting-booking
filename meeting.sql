PGDMP         $                x            meetingScheduler    13.0    13.0     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16394    meetingScheduler    DATABASE     n   CREATE DATABASE "meetingScheduler" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_India.1252';
 "   DROP DATABASE "meetingScheduler";
                postgres    false            �            1259    16395    meetinginfo    TABLE     s  CREATE TABLE public.meetinginfo (
    meetingid character varying(100) NOT NULL,
    organizername character varying(40),
    meetingtitle character varying(50),
    meetingdesc character varying(300),
    startdate timestamp without time zone,
    enddate timestamp without time zone,
    meetingstatus character varying(30),
    attendeeslist character varying(100)
);
    DROP TABLE public.meetinginfo;
       public         heap    postgres    false            �            1259    16402    usermeeting    TABLE       CREATE TABLE public.usermeeting (
    id integer NOT NULL,
    username character varying(100) NOT NULL,
    meetingid character varying(100),
    acceptancestate character varying(10),
    startdate timestamp without time zone,
    enddate timestamp without time zone
);
    DROP TABLE public.usermeeting;
       public         heap    postgres    false            �            1259    16400    usermeeting_id_seq    SEQUENCE     �   CREATE SEQUENCE public.usermeeting_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.usermeeting_id_seq;
       public          postgres    false    202            �           0    0    usermeeting_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.usermeeting_id_seq OWNED BY public.usermeeting.id;
          public          postgres    false    201            '           2604    16405    usermeeting id    DEFAULT     p   ALTER TABLE ONLY public.usermeeting ALTER COLUMN id SET DEFAULT nextval('public.usermeeting_id_seq'::regclass);
 =   ALTER TABLE public.usermeeting ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    202    201    202            �          0    16395    meetinginfo 
   TABLE DATA           �   COPY public.meetinginfo (meetingid, organizername, meetingtitle, meetingdesc, startdate, enddate, meetingstatus, attendeeslist) FROM stdin;
    public          postgres    false    200   n       �          0    16402    usermeeting 
   TABLE DATA           c   COPY public.usermeeting (id, username, meetingid, acceptancestate, startdate, enddate) FROM stdin;
    public          postgres    false    202   %       �           0    0    usermeeting_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.usermeeting_id_seq', 75, true);
          public          postgres    false    201            )           2606    16453    meetinginfo meetinginfo_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.meetinginfo
    ADD CONSTRAINT meetinginfo_pkey PRIMARY KEY (meetingid);
 F   ALTER TABLE ONLY public.meetinginfo DROP CONSTRAINT meetinginfo_pkey;
       public            postgres    false    200            ,           2606    16506    usermeeting usermeeting_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.usermeeting
    ADD CONSTRAINT usermeeting_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.usermeeting DROP CONSTRAINT usermeeting_pkey;
       public            postgres    false    202            *           1259    16461    user_id_index    INDEX     I   CREATE INDEX user_id_index ON public.usermeeting USING btree (username);
 !   DROP INDEX public.user_id_index;
       public            postgres    false    202            -           2606    16462    usermeeting fk_user    FK CONSTRAINT     �   ALTER TABLE ONLY public.usermeeting
    ADD CONSTRAINT fk_user FOREIGN KEY (meetingid) REFERENCES public.meetinginfo(meetingid);
 =   ALTER TABLE ONLY public.usermeeting DROP CONSTRAINT fk_user;
       public          postgres    false    2857    202    200            �   �   x�����0���)x �)mm�� �1t0q���t!�緃�,Ƴ���;��3#8�i�����Rǆ�.6��@�s�\&CkgR\$)��[e��SQ�uU�f�~r��S<������<��Ӟ��4��/~���X��o��s��	�����#�Hn      �   ~   x�37�J,��L2153�L��563K�51J4�MJJN�5N3OI5O3J2I4�ttvvqu�4202�54�52V04�26�20�&�en�TZP�Q��J�!�~!�!�a��[a��X��X�D+O��qqq h�E     